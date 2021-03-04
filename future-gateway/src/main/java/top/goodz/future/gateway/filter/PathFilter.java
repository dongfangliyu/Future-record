package top.goodz.future.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.goodz.future.gateway.endpoint.DiscoveryEndpointService;
import top.goodz.future.gateway.props.AuthProperties;
import top.goodz.future.gateway.provider.AuthProvider;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * <p>
 * 全局拦截器，作用所有的微服务
 * <p>
 * 1. 对请求头中参数进行处理 from 参数进行清洗
 * 2. 重写StripPrefix = 1,支持全局
 *
 * @author lengleng
 */
@Component
public abstract class PathFilter extends AbstractRouteFilter {

    private static final Logger log = LoggerFactory.getLogger(PathFilter.class);

   @Autowired
   private DiscoveryEndpointService discoveryEndpointService;

   @Autowired
    private AuthProperties authProperties;


   private static final String DEF_PORT = "80";

    @Override
    URI buildUri(ServerHttpRequest request, Route route) {

        if(!route.getId().contains("path")){
            return null;
        }

        URI oldUri = request.getURI(); // 127.0.0.1/api/auth
        String oldHost = oldUri.getHost(); // 127.0.0.1

        DiscoveryEndpointService.ServiceEndpoint instances = discoveryEndpointService.instances(route.getUri());

        String oldPath = request.getPath().value();

        if (StringUtils.isEmpty(oldPath) || StringUtils.isEmpty(instances)) {
            return null;
        }

        String newPort = null == DEF_PORT ? DEF_PORT :String.valueOf(instances.getPort());
        URI uri = null;
        try {
            uri = new URI(oldUri.getScheme(), oldUri.getUserInfo(), instances.getHost(), Integer.valueOf(newPort), oldUri.getPath(), oldUri.getQuery(), oldUri.getFragment());
            log.debug("route path URI{}", uri);
        } catch (URISyntaxException e) {
            log.error("route path filter error {}", e);
        }


        return uri;
    }

    private boolean isSkip(String path) {
        return AuthProvider.getDefaultSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::startsWith)
                || authProperties.getSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::startsWith);
    }


}
