package fun.lance.gateway.filter;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RateLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<RateLimiterGatewayFilterFactory.Config> {

    private final RateLimiterRegistry rateLimiterRegistry;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 获取RateLimiter
            RateLimiter rateLimiter = rateLimiterRegistry
                    .rateLimiter(config.getName(), config.getConfig());

            // 使用RateLimiter进行限流
            rateLimiter.acquirePermission();

            // 继续请求链
            return chain.filter(exchange);
        };
    }

    @Getter
    @Setter
    public static class Config {
        private String name;
        private RateLimiterConfig config;
    }
}

