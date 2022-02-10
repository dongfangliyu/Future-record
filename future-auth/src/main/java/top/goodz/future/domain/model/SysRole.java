package top.goodz.future.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class SysRole  implements GrantedAuthority {

    private Integer id;
    private String roleName;
    private String roleDesc;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return roleName;
    }
}
