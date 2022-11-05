package fun.lance.common.security.filters;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import fun.lance.common.handler.HttpHandler;
import fun.lance.common.resp.RespEnum;
import fun.lance.common.resp.ResultEntity;
import fun.lance.common.security.adapter.AuthConfigAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
//@Component
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final AuthConfigAdapter authConfigAdapter;
    private final HttpHandler httpHandler;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        List<String> excludePaths = authConfigAdapter.excludePathPatterns();
        if (CollUtil.isNotEmpty(excludePaths)) {
            for (String pathPattern : excludePaths) {
                AntPathMatcher matcher = new AntPathMatcher();
                if (matcher.match(pathPattern, req.getRequestURI())) {
                    filterChain.doFilter(req, resp);
                    return;
                }
            }
        }

        String accessToken = req.getHeader("Authorization");
        if (StrUtil.isBlank(accessToken)) {
            httpHandler.printToResponse(ResultEntity.fail(RespEnum.UNAUTHORIZED));
        }

    }
}
