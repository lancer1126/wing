package fun.lance.user.service;

import fun.lance.api.user.bo.UserInfoTokenBO;

public interface UserAccountService {
    UserInfoTokenBO getUserInfoToken(String username, String password, Integer sysType);
}
