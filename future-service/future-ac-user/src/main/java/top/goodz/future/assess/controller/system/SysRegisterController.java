package top.goodz.future.assess.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.goodz.future.response.CommonResponse;

/**
 *  @Description 注册 api
 *  @Author Yajun.Zhang
 *  @Date 2020/8/10 22:10
 */

@Controller
public class SysRegisterController {



    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public CommonResponse ajaxRegister(String loginName,String password)
    {

        System.out.println("dssg" + loginName + "p[ass" + password);
        return CommonResponse.isSuccess();
    }
}
