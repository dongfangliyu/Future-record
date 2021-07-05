package top.goodz.future.domian;

import top.goodz.future.domian.model.user.UserEntity;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public interface RegisterUserService {

    void register(UserEntity convert2UserEntity);
}
