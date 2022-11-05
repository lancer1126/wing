package fun.lance.user.service.impl;

import cn.hutool.core.util.StrUtil;
import fun.lance.api.user.model.dto.AuthDTO;
import fun.lance.api.user.model.vo.LoginVO;
import fun.lance.common.exception.WingException;
import fun.lance.common.security.model.bo.AuthAccountVerifyBO;
import fun.lance.user.manager.TokenManager;
import fun.lance.user.mapstruct.AccountStructMapper;
import fun.lance.user.service.UserAccountService;
import fun.lance.user.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    @Value("${user.auth.cache.expire-time}")
    private Long expiredTime;

    private final TokenManager tokenManager;
    private final AccountStructMapper accountStructMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginVO login(AuthDTO authDTO) {
        String username = authDTO.getPrincipal();
        String password = authDTO.getCredentials();

        if (StrUtil.isBlank(username)) {
            throw new WingException(MessageUtil.get("login.username.null"));
        }
        if (StrUtil.isBlank(password)) {
            throw new WingException(MessageUtil.get("login.password.null"));
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = null;
        try {
             authenticate = authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (authenticate == null) {
            throw new WingException(MessageUtil.get("login.error"));
        }

        AuthAccountVerifyBO principal = (AuthAccountVerifyBO) authenticate.getPrincipal();
        String token = tokenManager.storeToken(principal);

        LoginVO loginVO = accountStructMapper.convert(principal.getAuthAccount());
        loginVO.setToken(token);
        loginVO.setExpiredIn(expiredTime);
        return loginVO;
    }
}
