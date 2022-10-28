package fun.lance.user.service.impl;

import cn.hutool.core.util.StrUtil;
import fun.lance.api.user.bo.UserInfoTokenBO;
import fun.lance.common.exception.WingException;
import fun.lance.common.utils.PrincipalUtil;
import fun.lance.user.service.UserAccountService;
import fun.lance.user.utils.MessageUtil;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    @Override
    public UserInfoTokenBO getUserInfoToken(String username, String password, Integer sysType) {
        if (StrUtil.isBlank(username)) {
            throw new WingException(MessageUtil.get("login.username.null"));
        }
        if (StrUtil.isBlank(password)) {
            throw new WingException(MessageUtil.get("login.password.null"));
        }

        if (!PrincipalUtil.isUserName(username)) {
            throw new WingException(MessageUtil.get("login.username.notMatch"));
        }

        return null;
    }
}
