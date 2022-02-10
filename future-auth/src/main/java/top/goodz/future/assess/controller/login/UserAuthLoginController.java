package top.goodz.future.assess.controller.login;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import top.goodz.future.assess.model.CodeTokenLogin;
import top.goodz.future.assess.model.UserAuthLogin;
import top.goodz.future.assess.model.UserAuthLoginResponse;
import top.goodz.future.domain.constant.ConstantsOuath;
import top.goodz.future.domain.utils.TokenUtil;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.utils.HttpCloseableUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/oauth")
public class UserAuthLoginController {


    @ApiOperation(value = "密码模式登录方法", notes = "login")
    @PostMapping(value = "/login")
    @ApiImplicitParam(name = TokenUtil.USER_CODE_HEADER_KEY, value = "登录code", required = true, dataType = "string", paramType = "header")
    public CommonResponse<Map> login(@RequestBody @Valid UserAuthLogin userAuthLogin, HttpServletRequest request) {
        String userCode = request.getHeader(TokenUtil.USER_CODE_HEADER_KEY);
        Map loginResponse = null;
        String result = null;
        if (null != userCode || userCode.equalsIgnoreCase("user")) {

            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("username", userAuthLogin.getUsername()));
            list.add(new BasicNameValuePair("password", "123456"));
            list.add(new BasicNameValuePair("grant_type", ConstantsOuath.OAUTH_GRANT_TYPE_PWD));
            list.add(new BasicNameValuePair("scope", ConstantsOuath.OAUTH_SCOPES));
            list.add(new BasicNameValuePair("client_id", ConstantsOuath.OAUTH_WITHCLIENT));
            list.add(new BasicNameValuePair("client_secret", ConstantsOuath.OAUTH_SECRET));

            try {
                result = HttpCloseableUtils.doPostNameValuePairAddHeader("http://localhost:8081/oauth/token", list, TokenUtil.USER_CODE_HEADER_KEY, userCode);
                loginResponse = JSON.parseObject(result,Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return CommonResponse.responseOf(loginResponse);
    }

    @ApiOperation(value = "授权码获取", notes = "")
    @GetMapping(value = "/code")
    public CommonResponse code(HttpServletResponse httpServletResponse) {

        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("response_type", "code");
        map.put("scope", ConstantsOuath.OAUTH_SCOPES);
        map.put("client_id", ConstantsOuath.OAUTH_WITHCLIENT);


        Map loginResponse = null;
        String result = null;
        result = HttpCloseableUtils.sendGet("http://localhost:8081/oauth/authorize", map);

        try {
            if (result.contains("-1")) {
                httpServletResponse.sendRedirect("http://localhost:8081/login");
                return CommonResponse.responseOf("http://localhost:8081/login");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        loginResponse = JSON.parseObject(result, Map.class);

        return CommonResponse.responseOf(loginResponse);

    }

    @ApiOperation(value = "授权码获取token", notes = "授权码获取token")
    @PostMapping(value = "/code/token")
    @ApiImplicitParam(name = TokenUtil.USER_CODE_HEADER_KEY, value = "登录code", required = true, dataType = "string", paramType = "header")
    public CommonResponse<Map> token(@RequestBody CodeTokenLogin codeTokenLogin, HttpServletResponse httpServletResponse) {

        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("grant_type", ConstantsOuath.AUTHORIZATION_CODE));
        list.add(new BasicNameValuePair("scope", ConstantsOuath.OAUTH_SCOPES));
        list.add(new BasicNameValuePair("client_id", ConstantsOuath.OAUTH_WITHCLIENT));
        list.add(new BasicNameValuePair("client_secret", ConstantsOuath.OAUTH_SECRET));
        list.add(new BasicNameValuePair("code", codeTokenLogin.getCode()));
        list.add(new BasicNameValuePair("state", codeTokenLogin.getState()));

        Map loginResponse = null;
        String result = null;
        try {
            result = HttpCloseableUtils.doPostNameValuePairAddHeader("http://localhost:8081/oauth/token", list, TokenUtil.USER_CODE_HEADER_KEY, "future_Auth");
             loginResponse = JSON.parseObject(result,Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return CommonResponse.responseOf(loginResponse);

    }

    @GetMapping("getInfo")
    @ApiOperation(value = "登录方法", notes = "login")
    @PreAuthorize("hasAuthority('USER')")
    public CommonResponse userInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return CommonResponse.responseOf(authentication);
    }

    @GetMapping("getInfo2")
    @ApiOperation(value = "登录方法", notes = "login")
    public CommonResponse userInfo2() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.setAuthenticated(false);

        SecurityContextHolder.clearContext();
        return CommonResponse.responseOf(" hello world");
    }


}
