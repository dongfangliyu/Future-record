package top.goodz.future.application.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.goodz.future.application.process.model.SysLoginRequest;
import top.goodz.future.assess.model.SysLogin;
import top.goodz.future.domian.facade.SysLoginFacade;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/9/6 0:02
 */

@Service
public class SysLoginProcessService {


    @Resource
    private SysLoginFacade sysLoginFacade;

    public void login(SysLogin sysLogin) {

        sysLoginFacade.login(convert2SysLoginRequest(sysLogin));

    }

    private SysLoginRequest convert2SysLoginRequest(SysLogin sysLogin) {

        SysLoginRequest login = new SysLoginRequest();
        login.setUserName(sysLogin.getUserName());
        login.setPassword(sysLogin.getPassword());
        login.setValidateCode(sysLogin.getValidateCode());
        login.setRememberMe(sysLogin.getRememberMe());
        return login;
    }
}
