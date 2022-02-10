package top.goodz.future.infra.repostiory;

import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.utils.Func;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import top.goodz.future.domain.model.ldap.LDAPUserDetails;
import top.goodz.future.domain.repository.ldap.LDAPUserDetailsRepository;

import java.util.ArrayList;

@Slf4j
@Repository
public class LDAPUserDetailsRepositoryImpl  implements LDAPUserDetailsRepository {


    @Override
    public LDAPUserDetails findLDAPUserDetailsByName(String name, String password) {
        log.info("-------findLDAPUserDetailsByName params:{}",name);
        ArrayList<String> objects = new ArrayList<>();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        objects.add("USER");
        objects.add("ADMIN");
        return new LDAPUserDetails("u001","zhangyajun",passwordEncoder.encode("12345678"),true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList(
                Func.join(objects)));
    }
}
