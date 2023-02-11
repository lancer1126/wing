package fun.lance.user.service.impl;

import cn.hutool.core.util.StrUtil;
import fun.lance.common.exception.WingException;
import fun.lance.user.domain.dto.AuthDTO;
import fun.lance.user.domain.vo.LoginVO;
import fun.lance.user.service.UserAccountService;
import fun.lance.user.common.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    @Value("${user.auth.cache.expire-time:1}")
    private Long expiredTime;

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


        // 伪
        return new LoginVO();
    }
}
