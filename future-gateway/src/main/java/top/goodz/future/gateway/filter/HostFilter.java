package top.goodz.future.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * <p>
 * 全局拦截器，作用所有的微服务
 * <p>
 * 1. 对请求头中参数进行处理 from 参数进行清洗
 * 2. 重写StripPrefix = 1,支持全局
 *
 * @author zhangyajun
 */
@Component
public class HostFilter extends AbstractRouteFilter {

    private static final Logger log = LoggerFactory.getLogger(HostFilter.class);

    private static final String KEY_WORD = "x-app";

    @Override
    URI buildUri(ServerHttpRequest request, Route route) {

        if (!route.getId().contains("host")) {
            return null;
        }

        URI oldUri = request.getURI();
        String oldUriHost = oldUri.getHost();

        if (oldUriHost.indexOf(KEY_WORD) <= 0) {
            return null;
        }

        String[] segments = oldUriHost.split("\\.");

        int index = getKeyWordIndex(segments, KEY_WORD);

        if (index <= 0) {
            return null;
        }

        String newHost = segments[index - 1];

        String newPort = "80";
        URI uri = null;
        try {
            uri = new URI(oldUri.getScheme(), oldUri.getUserInfo(), newHost, Integer.valueOf(newPort), oldUri.getPath(), oldUri.getQuery(), oldUri.getFragment());
            log.debug("route host URI{}", uri);
        } catch (URISyntaxException e) {
            log.error("route host filter error {}", e);
        }


        return uri;
    }

    private int getKeyWordIndex(String[] segments, String keyWord) {
        for (int i = 0; i < segments.length; i++) {
            if (KEY_WORD.equals(segments[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
