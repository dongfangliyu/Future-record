package top.goodz.future.domain.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对请求头中的凭据做二次解析 同时设置到 Spring ScurityContextholder
 */
@Slf4j
@Component
public class SecurityTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        log.info("--SecurityTokenFilter req:{}", httpServletRequest.getMethod());


        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
