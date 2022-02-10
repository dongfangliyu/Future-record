package top.goodz.future.domian.repository;

import top.goodz.future.domian.model.user.SysUserEntity;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public interface RegisterUserRepository {
    void insert(SysUserEntity userEntity);

    void update(SysUserEntity userEntity);

    SysUserEntity loadByName(SysUserEntity userEntity);
}
