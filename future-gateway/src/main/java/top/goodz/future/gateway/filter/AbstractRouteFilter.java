package top.goodz.future.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * <p>
 * 全局拦截器，作用所有的微服务
 *
 * @author zhangyajun
 */
@Component
public abstract class AbstractRouteFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        Route route = (Route) exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

        if (null == route) {
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();

        URI newUri = buildUri(request, route);

        if (null == newUri) {
            return chain.filter(exchange);
        }

        Route.AsyncBuilder asyncBuilder = Route.async();
        Route routes = asyncBuilder.asyncPredicate(route.getPredicate())
                .filters(route.getFilters())
                .id(route.getId())
                .uri(newUri)
                .order(route.getOrder())
                .build();


        exchange.getAttributes().put(GATEWAY_ROUTE_ATTR, routes);

        return chain.filter(exchange);
    }

    abstract URI buildUri(ServerHttpRequest request, Route route);

    @Override
    public int getOrder() {
        return -1;
    }

}
