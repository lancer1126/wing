package fun.lance.user.service.impl;

import fun.lance.api.user.bo.UserInfoTokenBO;
import fun.lance.user.service.UserAccountService;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    @Override
    public UserInfoTokenBO getUserInfoToken(String username, String password, Integer sysType) {
        return null;
    }
}
