package top.goodz.future.domain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


/**
 *
 * 此配置类 是开启方法权限注解  可以使用 @preAuthorize() @preFilter() 等注解
 *
 * zhangyajun
 *
 */
@Configuration
@EnableGlobalMethodSecurity( prePostEnabled   = true)
public class MethodSecurityConfig {
}
