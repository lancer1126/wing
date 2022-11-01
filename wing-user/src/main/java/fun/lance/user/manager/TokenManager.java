package fun.lance.user.manager;

import fun.lance.api.user.bo.UserInfoTokenBO;
import fun.lance.api.user.vo.TokenInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RefreshScope
@RequiredArgsConstructor
public class TokenManager {

    public TokenInfoVO storeAccessToken(UserInfoTokenBO userInfoTokenBO) {
        // todo 生成token
        return null;
    }
}
