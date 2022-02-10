package top.goodz.future.domian;

import top.goodz.future.domian.model.user.SysUserEntity;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public interface RegisterUserService {

    SysUserEntity register(SysUserEntity convert2UserEntity);

    void active(SysUserEntity userEntity);
}
