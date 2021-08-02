package top.goodz.future.infra.dao;

import org.apache.ibatis.annotations.Param;
import top.goodz.future.domian.model.secuiity.UserSecurity;

public interface UserSecurityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserSecurity record);

    int insertSelective(UserSecurity record);

    UserSecurity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserSecurity record);

    int updateByPrimaryKey(UserSecurity record);

    UserSecurity selectBySecurityNo(@Param("securityNo") String securityNo);
}