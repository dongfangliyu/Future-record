
package top.goodz.future.domain.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import top.goodz.future.domain.config.support.AuthExceptionEntryPoint;
import top.goodz.future.domain.constant.ConstantsOuath;

import java.util.Arrays;

/**
 * 资源服务
 *
 * @author zhangyajun
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	/**
	 * 资源请求规则
	 * @param httpSecurity
	 */
	@Override
	@SneakyThrows
	public void configure(HttpSecurity httpSecurity) {
		httpSecurity
				.antMatcher("/**").authorizeRequests()
				.antMatchers("/swagger-ui.html","/swagger-resources/**")
				.permitAll()
				.anyRequest().authenticated()
				//指定不同请求方式访问资源所需要的权限，一般查询是read，其余是write。
				.antMatchers("/**").access("#oauth2.hasScope('all')")

			/*	.antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
				.antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
				.antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
				.antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
				.antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")*/
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.csrf().disable()
				.httpBasic()
				.and()
				.headers().addHeaderWriter((request, response) -> {
					response.addHeader("Access-Control-Allow-Origin", "*");//允许跨域
					if (request.getMethod().equals("OPTIONS")) {//如果是跨域的预检请求，则原封不动向下传达请求头信息
						response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
						response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
					}
				});
	}


	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

		resources.resourceId(ConstantsOuath.FUTURE_RESOURCE_ID)
				.authenticationEntryPoint(new AuthExceptionEntryPoint())
				.stateless(true);

	}


	/**
	 * 远程调用
	 * @return
	 */
/*	@Bean
	public RemoteTokenServices remoteTokenServices(){
		RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
		remoteTokenServices.setCheckTokenEndpointUrl("http://localhost/api/auth/oauth/check_token");
		remoteTokenServices.setClientId(ConstantsOuath.OAUTH_WITHCLIENT);
		remoteTokenServices.setClientSecret(ConstantsOuath.OAUTH_SECRET);
		return remoteTokenServices;
	}*/

}
