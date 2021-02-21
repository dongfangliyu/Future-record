package top.goodz.future.domian.facade;

import top.goodz.future.application.process.model.SysLoginRequest;
import top.goodz.future.assess.model.SysLogin;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public interface SysLoginFacade {
    void login(SysLoginRequest convert2SysLoginRequest);
}
