package top.goodz.future.infra.dao;

import org.apache.ibatis.annotations.Param;
import top.goodz.future.domian.model.user.SysUserEntity;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */

public interface UserDao {
    void insert(SysUserEntity userEntity);

    int update(@Param("entity") SysUserEntity userEntity);

    SysUserEntity loadByName (@Param("entity") SysUserEntity userEntity);
}
