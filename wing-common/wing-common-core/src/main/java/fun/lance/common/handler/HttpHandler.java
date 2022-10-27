package fun.lance.common.handler;

import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.JSON;
import fun.lance.common.exception.WingException;
import fun.lance.common.resp.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class HttpHandler {
    public <T> void printToResponse(ResultEntity<T> result) {
        if (result == null) {
            log.info("print obj is null");
            return;
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            log.info("requestContextHolder is null");
            return;
        }

        HttpServletResponse response = attributes.getResponse();
        if (response == null) {
            log.info("httpServletResponse is null");
            return;
        }

        log.error("response error: " + result.getMsg());
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        PrintWriter printWriter;
        try {
            printWriter = response.getWriter();
            printWriter.write(JSON.toJSONString(result));
        } catch (IOException e) {
            throw new WingException("printToResponse异常");
        }

    }
}
