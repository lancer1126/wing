package fun.lance.common.security.common;

import java.util.Arrays;
import java.util.List;

public class SecurityConst {

    public static final List<String> IGNORE_URLS = Arrays.asList(
            "/webjars/**", "/doc.html", "/swagger-resources/**", "/v2/api-docs",
            "/ua/**"
    );
}
