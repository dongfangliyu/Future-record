package top.goodz.future.domain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;


/**
 *
 * 此配置类 是开启方法权限注解  可以使用 @preAuthorize() @preFilter() 等注解
 *
 * zhangyajun
 *
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class MethodSecurityConfig  extends GlobalMethodSecurityConfiguration {

    /**
     * 要启用＃oAuth2安全表达式，只需要将默认表达式处理程序设置为OAuth2MethodSecurityExpressionHandler
     *  而不是DefaultMethodSecurityExpressionHandler。
     * 由于OAuth2MethodSecurityExpressionHandler仍然对其进行了扩展，因此整个先前的功能保持不变。
     * 在我的配置中，我同时使用了GlobalMethodSecurityConfiguration和WebSecurityConfigurerAdapter。
     * @return
     */

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }
}
