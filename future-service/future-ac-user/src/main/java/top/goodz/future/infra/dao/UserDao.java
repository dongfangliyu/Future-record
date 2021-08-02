package top.goodz.future.infra.dao;

import org.apache.ibatis.annotations.Param;
import top.goodz.future.domian.model.user.UserEntity;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */

public interface UserDao {
    void insert(UserEntity userEntity);

    int update(@Param("entity") UserEntity userEntity);

    UserEntity loadByName (@Param("entity") UserEntity userEntity);
}
