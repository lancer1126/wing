package fun.lance.user.service;

import com.alipay.api.AlipayClient;
import fun.lance.common.bean.user.dto.AlipayAuthDTO;
import fun.lance.common.bean.user.vo.AlipayAuthVO;

public interface AlipayService {

    /**
     * 获取授权地址
     */
    AlipayAuthVO getRedirectUrl();

    /**
     * 使用授权地址请求token
     */
    AlipayAuthVO alipayCallback(AlipayAuthDTO alipayDTO);

    /**
     * 封装AlipayCLient
     */
    AlipayClient parseAlipayClient();
}
