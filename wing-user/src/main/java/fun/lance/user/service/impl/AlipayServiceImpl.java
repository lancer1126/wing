package fun.lance.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import fun.lance.user.common.constants.AlipayConst;
import fun.lance.common.constants.enums.DataFormatEnum;
import fun.lance.common.exception.WingException;
import fun.lance.user.domain.bo.AlipayAuthRespBO;
import fun.lance.user.domain.dto.AlipayAuthDTO;
import fun.lance.user.domain.vo.AlipayAuthVO;
import fun.lance.user.service.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {

    @Value("${alipay.oauth.appid:1}")
    private String appId;
    @Value("${alipay.oauth.private-key:1}")
    private String privateKey;
    @Value("${alipay.oauth.alipay-public-key:1}")
    private String alipayPublicKey;
    @Value("${alipay.oauth.sign-type:1}")
    private String signType;
    @Value("${alipay.oauth.callback-url:1}")
    private String callBackUrl;
    @Value("${alipay.oauth.scope:1}")
    private String scope;

    @Override
    public AlipayAuthVO getRedirectUrl() {
        String redirectUrl = AlipayConst.AUTH_BASE_URL
                + "app_id=" + appId
                + "&scope=" + scope
                + "&redirect_uri=" + URLEncoder.encode(callBackUrl, StandardCharsets.UTF_8);
        AlipayAuthVO alipayAuthVO = new AlipayAuthVO();
        alipayAuthVO.setRedirectUrl(redirectUrl);
        return alipayAuthVO;
    }

    @Override
    public AlipayAuthVO alipayCallback(AlipayAuthDTO alipayDTO) {
        if (StrUtil.isBlank(alipayDTO.getAuthCode())) {
            throw new WingException("Alipay auth code is null");
        }

        // FASTJSON自动映射下划线
        ParserConfig snakeToCamelConfig = new ParserConfig();
        snakeToCamelConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;

        AlipayAuthVO alipayAuthVO;
        AlipayClient alipayClient = parseAlipayClient();

        try {
            // 获取accessToken和userId
            AlipaySystemOauthTokenRequest tokenRequest = new AlipaySystemOauthTokenRequest();
            tokenRequest.setCode(alipayDTO.getAuthCode());
            tokenRequest.setGrantType(AlipayConst.DEFAULT_GRANT_TYPE);
            AlipaySystemOauthTokenResponse response = alipayClient.execute(tokenRequest);

            AlipayAuthRespBO respBO = JSON.parseObject(response.getBody(), AlipayAuthRespBO.class, snakeToCamelConfig);
            alipayAuthVO = respBO.getAlipaySystemOauthTokenResponse();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WingException("Get token from alipay error");
        }

        try {
            // 获取用户信息
            AlipayUserInfoShareRequest userInfoRequest = new AlipayUserInfoShareRequest();
            AlipayUserInfoShareResponse response = alipayClient
                    .execute(userInfoRequest, alipayAuthVO.getAccessToken());

            AlipayAuthRespBO userInfoBO = JSON.parseObject(response.getBody(), AlipayAuthRespBO.class, snakeToCamelConfig);
            alipayAuthVO.setAvatar(userInfoBO.getAlipayUserInfoShareResponse().getAvatar());
            alipayAuthVO.setNickName(userInfoBO.getAlipayUserInfoShareResponse().getNickName());
        } catch (Exception ae) {
            ae.printStackTrace();
            throw new WingException("Get user info from alipay error");
        }
        return alipayAuthVO;
    }

    @Override
    public AlipayClient parseAlipayClient() {
        AlipayConfig config = new AlipayConfig();
        config.setServerUrl(AlipayConst.ALIPAY_GATEWAY);
        config.setAppId(appId);
        config.setPrivateKey(privateKey);
        config.setAlipayPublicKey(alipayPublicKey);
        config.setFormat(DataFormatEnum.JSON.value());
        config.setCharset(StandardCharsets.UTF_8.toString());
        config.setSignType(signType);

        AlipayClient alipayClient;
        try {
            alipayClient = new DefaultAlipayClient(config);
        } catch (AlipayApiException e) {
            throw new WingException("parse AlipayClient error");
        }
        return alipayClient;
    }
}
