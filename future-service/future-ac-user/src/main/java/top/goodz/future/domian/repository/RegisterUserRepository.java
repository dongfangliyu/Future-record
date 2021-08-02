package top.goodz.future.domian.repository;

import top.goodz.future.domian.model.user.UserEntity;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public interface RegisterUserRepository {
    void insert(UserEntity userEntity);

    void update(UserEntity userEntity);

    UserEntity loadByName(UserEntity userEntity);
}
