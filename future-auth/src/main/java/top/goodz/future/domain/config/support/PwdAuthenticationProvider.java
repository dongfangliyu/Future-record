/*
package top.goodz.future.domain.config.support;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import top.goodz.future.enums.ErrorCodeEnum;

@Component
public class PwdAuthenticationProvider extends UsernamePasswordAuthenticationFilter implements AuthenticationProvider  {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    //认证方法
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken adminLoginToken = (UsernamePasswordAuthenticationToken) authentication;
        System.out.println("===进入Admin密码登录验证环节=====" + JSON.toJSONString(adminLoginToken));
        UserDetails userDetails = userDetailsService.loadUserByUsername(adminLoginToken.getName());
        //matches方法，前面为明文，后续为加密后密文
        //匹配密码。进行密码校验
        if (passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

            return authentication;
        }
        ErrorCodeEnum.PASSWORD_ERROR.throwEcxeption();


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}*/
