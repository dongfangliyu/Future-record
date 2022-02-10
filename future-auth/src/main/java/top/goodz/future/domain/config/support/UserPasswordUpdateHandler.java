package top.goodz.future.domain.config.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserPasswordUpdateHandler implements UserDetailsPasswordService {
    @Override
    public UserDetails updatePassword(UserDetails userDetails, String   password) {

        log.info("UserPasswordUpdateHandler updatePassword {} ", password );

        return userDetails;
    }
}
