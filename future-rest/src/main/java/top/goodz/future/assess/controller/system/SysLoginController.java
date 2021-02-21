package top.goodz.future.assess.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.goodz.future.application.process.SysLoginProcessService;
import top.goodz.future.assess.model.SysLogin;
import top.goodz.future.response.CommonResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 登录相关
 * @Author Yajun.Zhang
 * @Date 2020/8/8 17:00
 */
@Controller
public class SysLoginController {

    @Resource
    private SysLoginProcessService  sysLoginProcessService;

    @RequestMapping(value = "/sgin_in", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {

        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public CommonResponse ajaxLogin(String username, String password, String validateCode,Boolean rememberMe)
    {
        System.out.println("par" + username + "" + password  + ""  + validateCode +"" +rememberMe);


        sysLoginProcessService.login(convert2SysLogin(username,password,validateCode,rememberMe));
        return  CommonResponse.isSuccess();
    }

    private SysLogin convert2SysLogin(String username, String password, String validateCode, Boolean rememberMe) {

        SysLogin login = new SysLogin();
        login.setUserName(username);
        login.setPassword(password);
        login.setValidateCode(validateCode);
        login.setRememberMe(rememberMe);
        return login;
    }


}
