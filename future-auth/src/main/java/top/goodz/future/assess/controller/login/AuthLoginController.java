package top.goodz.future.assess.controller.login;


import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.security.SecurityUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import top.goodz.future.assess.model.UserAuthLogin;
import top.goodz.future.assess.model.UserAuthLoginResponse;
import top.goodz.future.domain.constant.ConstantsOuath;
import top.goodz.future.domain.utils.JwtUtil;
import top.goodz.future.domain.utils.TokenUtil;
import top.goodz.future.domian.model.user.SysUserEntity;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.service.dubbo.UserDetailsRpcService;
import top.goodz.future.utils.HttpCloseableUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AuthLoginController {


    @GetMapping("getInfo2")
    @ApiOperation(value = "登录方法1", notes = "login")
    public CommonResponse userInfo2(@RequestParam int name1){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.setAuthenticated(false);


        return CommonResponse.isSuccess();
    }

    @GetMapping("/clear")
    @ApiOperation(value = "登录方法1", notes = "login")
    public CommonResponse U(@RequestParam String name1){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.setAuthenticated(false);

        SecurityContextHolder.clearContext();


        return CommonResponse.isSuccess();
    }



}
