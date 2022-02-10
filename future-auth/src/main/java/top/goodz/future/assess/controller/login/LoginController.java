package top.goodz.future.assess.controller.login;


import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.goodz.future.response.CommonResponse;

@RestController
public class LoginController {


    @RequestMapping("/success")
    @ApiOperation(value = "登录方法1", notes = "login")
    public CommonResponse userInfo2(@RequestParam int name1){


        return CommonResponse.responseOf("ok");
    }



}
