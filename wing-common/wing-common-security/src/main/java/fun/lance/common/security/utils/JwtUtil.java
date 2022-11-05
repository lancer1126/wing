package fun.lance.common.security.utils;

import cn.hutool.jwt.JWT;
import fun.lance.api.user.model.dao.AuthAccount;
import fun.lance.common.exception.WingException;
import fun.lance.common.security.model.bo.AuthAccountVerifyBO;

import java.nio.charset.StandardCharsets;

public class JwtUtil {

    public static String createJwt(AuthAccountVerifyBO verifyBO, String key) {
        return JWT.create()
                .setPayload("userId", verifyBO.getAuthAccount().getUserId())
                .setPayload("username", verifyBO.getUsername())
                .setKey(key.getBytes(StandardCharsets.UTF_8))
                .sign();
    }

    public static AuthAccountVerifyBO decodeToken(String token) {
        AuthAccountVerifyBO verifyBO = new AuthAccountVerifyBO();
        AuthAccount authAccount = new AuthAccount();

        try {
            JWT jwt = JWT.of(token);
            authAccount.setUserId((Long) jwt.getPayload("userId"));
            authAccount.setUsername((String) jwt.getPayload("username"));
            verifyBO.setAuthAccount(authAccount);
        } catch (Exception e) {
            throw new WingException("decode token error, token: " + token);
        }
        return verifyBO;
    }
}
