package top.goodz.future.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.goodz.future.domain.repository.ldap.LDAPUserDetailsRepository;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.exception.CommonException;
import top.goodz.future.exception.ServiceException;

/**
 * AbstractUserDetailsAuthenticationProvider 该类是最要对AuthentcaionToken 做验证的类
 * <p>
 * zhangyajun
 */

@Service
public class LDAPDetailServiceProvider extends AbstractUserDetailsAuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private LDAPUserDetailsRepository ldapUserDetailsRepository;

    public LDAPDetailServiceProvider(LDAPUserDetailsRepository ldapUserDetailsRepository) {
        this.ldapUserDetailsRepository = ldapUserDetailsRepository;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        // 检查忽略
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(usernamePasswordAuthenticationToken.getCredentials().toString(), userDetails.getPassword());

        if (!matches) {
            throw new BadCredentialsException(ErrorCodeEnum.PASSWORD_ERROR.getMessage());

        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        return ldapUserDetailsRepository.findLDAPUserDetailsByName(username, usernamePasswordAuthenticationToken.getCredentials().toString());
    }
}
