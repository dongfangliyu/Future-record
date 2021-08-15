package top.goodz.future.infra.facade;

import org.springframework.stereotype.Service;
import top.goodz.future.application.process.model.SysLoginRequest;
import top.goodz.future.domian.facade.SysLoginFacade;
import top.goodz.future.domian.model.SysLoginRequestBean;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.feigin.IFutureUserClient;
import top.goodz.future.response.CommonResponse;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/9/6 0:03
 */
@Service
public class SysLoginFacadeImpl implements SysLoginFacade {

    @Resource
    private IFutureUserClient futureUserClient;


    @Override
    public void login(SysLoginRequest request) {
        CommonResponse response = futureUserClient.login(convert2SysLoginRequestBean(request));

        if (!response.getCode().equals(ErrorCodeEnum.SUCCESS.getCode())) {
            ErrorCodeEnum.ERROR.throwEcxeption();
        }
    }

    private SysLoginRequestBean convert2SysLoginRequestBean(SysLoginRequest sysLogin) {

        SysLoginRequestBean login = new SysLoginRequestBean();
        login.setUserName(sysLogin.getUserName());
        login.setPassword(sysLogin.getPassword());
        login.setValidateCode(sysLogin.getValidateCode());
        login.setRememberMe(sysLogin.getRememberMe());
        return login;
    }
}
