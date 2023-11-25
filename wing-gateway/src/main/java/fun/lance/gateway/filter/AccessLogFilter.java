package fun.lance.gateway.filter;

import fun.lance.gateway.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessLogFilter implements GlobalFilter, Ordered {

    @Value("${wing.gateway.order}")
    private Integer gatewayOrder;

    @Override
    public int getOrder() {
        return gatewayOrder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Route route = getGatewayRoute(exchange);

        String ipAddr = IpUtil.getIpAddr(request);
        log.info("----------------REQUEST-------------------");
        log.info("URI        : {}", request.getURI());
        log.info("Method     : {}", request.getMethodValue());
        log.info("Path       : {}", request.getPath().pathWithinApplication().value());
        log.info("Server     : {}", route.getId());
        log.info("Time       : {}", new Date());
        log.info("IP Address : {}", ipAddr);
        log.info("------------------------------------------");
        return chain.filter(exchange);
    }

    private Route getGatewayRoute(ServerWebExchange exchange) {
        return exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
    }
}
