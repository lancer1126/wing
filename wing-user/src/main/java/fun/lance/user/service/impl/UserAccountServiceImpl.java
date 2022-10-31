package fun.lance.user.service.impl;

import cn.hutool.core.util.StrUtil;
import fun.lance.api.user.bo.UserInfoTokenBO;
import fun.lance.common.exception.WingException;
import fun.lance.common.utils.PrincipalUtil;
import fun.lance.user.converter.AccountConverter;
import fun.lance.user.enums.AccountStatusEnum;
import fun.lance.user.mapper.AuthAccountMapper;
import fun.lance.user.service.UserAccountService;
import fun.lance.user.utils.MessageUtil;
import fun.lance.wing.common.security.bo.AuthAccountVerifyBO;
import fun.lance.wing.common.security.enums.UserNameEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final AuthAccountMapper authAccountMapper;
    private final PasswordEncoder passwordEncoder;
    private final AccountConverter accountConverter;

    @Override
    public UserInfoTokenBO getUserInfoToken(String username, String password, Integer sysType) {
        if (StrUtil.isBlank(username)) {
            throw new WingException(MessageUtil.get("login.username.null"));
        }
        if (StrUtil.isBlank(password)) {
            throw new WingException(MessageUtil.get("login.password.null"));
        }

        UserNameEnum userNameEnum = null;
        if (PrincipalUtil.isUserName(username)) {
            userNameEnum = UserNameEnum.USERNAME;
        }
        if (userNameEnum == null) {
            throw new WingException(MessageUtil.get("login.username.notMatch"));
        }

        AuthAccountVerifyBO acctVerifyBO = authAccountMapper
                .getAuthAccountInfoByUsername(userNameEnum.value(), username, sysType);
         if (acctVerifyBO == null) {
            throw new WingException(MessageUtil.get("login.username.notExist"));
        }
        if (Objects.equals(acctVerifyBO.getStatus(), AccountStatusEnum.DISABLE.value())) {
            throw new WingException(MessageUtil.get("login.username.disable"));
        }
        if (!passwordEncoder.matches(password, acctVerifyBO.getPassword())) {
            throw new WingException(MessageUtil.get("login.error"));
        }
        UserInfoTokenBO convert = accountConverter.convert(acctVerifyBO);
        return convert;
    }
}
