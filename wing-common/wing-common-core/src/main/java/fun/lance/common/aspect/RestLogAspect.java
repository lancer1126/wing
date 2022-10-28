package fun.lance.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class RestLogAspect {

    @Pointcut("execution(public * fun.lance.*.controller..*(..))")
    public void restLog() {}

    @Before("restLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return;

        HttpServletRequest request = attributes.getRequest();
        log.info("--------------------------------------- Start ----------------------------------------");
        log.info("URL          : {}", request.getRequestURL().toString());
        log.info("Class Method : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("IP           : {}", request.getRemoteAddr());
        log.info("Request Args : {}", joinPoint.getArgs());
        log.info("---------------------------------------- End -----------------------------------------");
    }
}
