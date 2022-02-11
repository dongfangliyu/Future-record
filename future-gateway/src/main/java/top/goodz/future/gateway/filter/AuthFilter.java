/*
 *
 *  Author: YaJun .Zhang (dongfangliyu@gmail.com)
 */

package top.goodz.future.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.goodz.future.gateway.props.AuthProperties;
import top.goodz.future.gateway.provider.AuthProvider;
import top.goodz.future.gateway.provider.ResponseProvider;

import java.net.URI;
import java.nio.charset.StandardCharsets;


/**
 * 鉴权认证
 *
 * @author zhangyajun
 */

@Slf4j
@Component
@AllArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    private AuthProperties authProperties;
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (isSkip(path)) {
            return chain.filter(exchange);
        }
        ServerHttpResponse resp = exchange.getResponse();
        String headerToken = exchange.getRequest().getHeaders().getFirst(AuthProvider.AUTH_KEY);
        String paramToken = exchange.getRequest().getQueryParams().getFirst(AuthProvider.AUTH_KEY);
        if (StringUtils.isAllBlank(headerToken)) {
            return unAuth(resp, "缺失令牌,鉴权失败");
        }
		/*String auth = StringUtils.isBlank(headerToken) ? paramToken : headerToken;
		String token = JwtUtil.getToken(auth);
		Claims claims = JwtUtil.parseJWT(token);
		if (claims == null) {
			return unAuth(resp, "请求未授权");
		}*/
        Object tokenObject = null;
        try {
			/*String usertype = String.valueOf(ChkUtI.isNotEmpty(claims.get(TokenUserInfo.USER_TYPE)) ? claims.get(TokenUserInfo.USER_TYPE) : "");
			String username = String.valueOf(ChkUtil.isNotEmpty(claims.get(TokenUserInfo.USER_NAME)) ? claims.get(TokenUserInfo.USER_NAME) : "");
			tokenObject = redisUtil.get(usertype + "|" + username);
			LOGGER.info("查询出的token:" + tokenObject);
			if (ChkUtil.isEmpty(tokenObject)) {
				return unAuth(resp, "登录失效");
			}
			JSONObject json = JSONObjecT.fromObject(tokenObject);
			String ip = json.getString("ip");
			String ipAddress = RemoteUtil.getRemoteHost(exchange);
			if (!ip.equals(ipAddress)) {
				return unAuth(resp, "登录失效");
			}*/
        } catch (Exception e) {
            LOGGER.info("拦截器获取token异常，{}", e.getMessage(), e);
        }
        return chain.filter(exchange);
    }

    private boolean isSkip(String path) {
        return AuthProvider.getDefaultSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::startsWith)
                || authProperties.getSkipUrlList().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::startsWith);
    }

    private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = "";
        try {
            result = objectMapper.writeValueAsString(ResponseProvider.unAuth(msg));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -10;
    }

}

