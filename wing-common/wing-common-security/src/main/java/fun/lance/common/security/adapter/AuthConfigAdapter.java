package fun.lance.common.security.adapter;

import fun.lance.common.feign.FeignInsideAuthConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthConfigAdapter {

    /**
     * 内部直接调用接口，无需登录权限
     */
    private static final String FEIGN_INSIDER_URI = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/**";

    /**
     * 外部直接调用接口，无需登录权限 unwanted auth
     */
    private static final String EXTERNAL_URI = "/**/ua/**";

    /**
     * swagger
     */
    private static final String DOC_URI = "/v2/api-docs";

    public List<String> excludePathPatterns(String... paths) {
        List<String> excludeList = new ArrayList<>();
        excludeList.add(DOC_URI);
        excludeList.add(EXTERNAL_URI);
        excludeList.add(FEIGN_INSIDER_URI);
        excludeList.addAll(Arrays.asList(paths));
        return excludeList;
    }

}
