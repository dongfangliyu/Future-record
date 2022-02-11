
package top.goodz.future.domain.model.ldap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.Mac;
import javax.naming.CompositeName;
import javax.naming.Name;
import java.util.Collection;
import java.util.Date;

/**
 * 支持LDAP 用户扩展类
 *
 * @author zhangyajun
 */

@Getter
@Entry(objectClasses = {"inetOrgPerson", "organizationalPersonm", "person", "top"})
public class LDAPUserDetails extends User {

 /*   @Id
    @JsonIgnore
    private Name id;*/
    @Attribute(name = "uid")
    private String userName;
    @Attribute(name = "userPassword")
    private String password;


    @SneakyThrows
    public LDAPUserDetails(String id, String username, String password,
                           boolean enabled, boolean accountNonExpired,
                           boolean credentialsNonExpired, boolean accountNonLocked,
                           Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userName = username;
        this.password = password;
    }
}
