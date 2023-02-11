package fun.lance.common.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collections;
import java.util.Map;

public class SecurityUtil {

    /**
     * 获取会员Id
     */
    public static String getMemberId() {
        String memberId;
        try {
            memberId = getTokenAttributes().get("memberId").toString();
        } catch (Exception e) {
            memberId = "";
        }
        return memberId;
    }

    public static Map<String, Object> getTokenAttributes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof JwtAuthenticationToken jwtToken) {
                return jwtToken.getTokenAttributes();
            }
        }
        return Collections.emptyMap();
    }

}
