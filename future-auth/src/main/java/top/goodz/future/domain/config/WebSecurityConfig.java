package top.goodz.future.domain.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.goodz.future.domain.config.support.UserPasswordUpdateHandler;
import top.goodz.future.domain.config.support.UsernamePasswordFilter;
import top.goodz.future.domain.constant.ConstantsOuath;
import top.goodz.future.domain.filter.SecurityTokenFilter;
import top.goodz.future.domain.repository.ldap.LDAPUserDetailsRepository;
import top.goodz.future.domain.service.SysUserService;
import top.goodz.future.domain.service.impl.LDAPDetailServiceProvider;

/**
 * zhangyajun  2022-1-19
 */
@Order(2)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LDAPUserDetailsRepository ldapUserDetailsRepository;
    @Autowired
    private SecurityTokenFilter securityTokenFilter;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
 //   private PwdAuthenticationProvider adminPwdAuthenticationProvider;

    /**
     * 设置给认证管理器  在内存中进行注册公开内存的身份验证
     * 可配置多provider
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(daoAuthenticationProvider());
    //            .authenticationProvider(adminPwdAuthenticationProvider);
           //     .authenticationProvider(ldapDetailServiceProvider());
    }

    /**
     * 注入实现了 UserDetailService 的bean
     *
     * @return
     */
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(sysUserService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsPasswordService(new UserPasswordUpdateHandler());
        return provider;
    }
    /**
     * ldap
     * @return
     */
    @Bean
    LDAPDetailServiceProvider ldapDetailServiceProvider() {
        return new LDAPDetailServiceProvider(ldapUserDetailsRepository);
    }


    /**
     * 密码编码器
     *
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 密码模式认证管理器 配置
     *
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UsernamePasswordFilter usernamePasswordFilter() throws Exception {
        UsernamePasswordFilter filter = new UsernamePasswordFilter();
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    /**
     * 配置 HttpSecurity 对不同路径执行不同策略
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //所有资源必须授权后访问
        httpSecurity
        .requestMatchers()
                .antMatchers(ConstantsOuath.URLPREFIX + "/**","/api/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers(ConstantsOuath.URLPREFIX + "/**","/api/oauth/**")
                .permitAll();
        httpSecurity.httpBasic().and().csrf().disable();


    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/public/**", "/error/**")
                .antMatchers("/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals","/v2/api-docs")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 静态资源放行
    }
}
