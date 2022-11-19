package fun.lance.user.manager;

import com.alibaba.fastjson.JSON;
import fun.lance.common.constants.UserConst;
import fun.lance.common.security.model.AuthAccountVerifyBO;
import fun.lance.common.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RefreshScope
@RequiredArgsConstructor
public class TokenManager {

    @Value("${user.auth.token.sign-key}")
    private String tokenSignKey;
    @Value("${user.auth.cache.expire-time}")
    private Long expiredTime;

    private final RedisTemplate<Object, Object> redisTemplate;

    public String storeToken(AuthAccountVerifyBO verifyBO) {
        String token = JwtUtil.createJwt(verifyBO, tokenSignKey);
        String tokenKey = UserConst.CACHE_PREFIX + token;
        redisTemplate.opsForValue()
                .set(tokenKey, JSON.toJSONString(verifyBO), expiredTime, TimeUnit.SECONDS);
        return token;
    }
}
