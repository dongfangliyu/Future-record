package top.goodz.future.domain.repository.ldap;

import top.goodz.future.domain.model.ldap.LDAPUserDetails;

public interface LDAPUserDetailsRepository {
    LDAPUserDetails findLDAPUserDetailsByName(String name,String password);
}
