package top.goodz.future.assess.controller.login;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;
import top.goodz.future.application.process.OauthLoginProcessService;
import top.goodz.future.assess.model.UserAuthLoginRequest;
import top.goodz.future.assess.model.UserAuthLoginResponse;
import top.goodz.future.domain.constant.ConstantsOuath;
import top.goodz.future.domain.model.vo.UserAuthLoginVO;
import top.goodz.future.domain.service.oauth2.Oauth2LoginAuthorizationService;
import top.goodz.future.domain.utils.TokenUtil;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.utils.ChkUtil;
import top.goodz.tools.annotation.Logger;
import top.goodz.tools.annotation.UserAuthToken;
import top.goodz.tools.annotation.UserPermissions;
import top.goodz.tools.model.TokenUserInfo;
import top.goodz.tools.utils.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 申请令牌api控制器
 */
@RestController
@RequestMapping("/api/oauth")
public class UserLoginController {

    @Autowired
    Oauth2LoginAuthorizationService oauth2LoginAuthorizationService;

    @Autowired
    private OauthLoginProcessService oauthLoginProcessService;

    @Autowired
    @Qualifier("consumerTokenServices")
    ConsumerTokenServices consumerTokenServices;



    @ApiOperation(value = "密码模式登录方法")
    @PostMapping(value = "/login")
    @ApiImplicitParam(name = TokenUtil.USER_CODE_HEADER_KEY, value = "用户code", required = true, dataType = "string", paramType = "header")
    public CommonResponse<UserAuthLoginResponse> password(@RequestBody @Valid UserAuthLoginRequest userAuthLogin, HttpServletRequest request) {
        String userCode = request.getHeader(TokenUtil.USER_CODE_HEADER_KEY);

        if (null == userCode || !ConstantsOuath.FUTURE_USER.equalsIgnoreCase(userCode)) {
            ErrorCodeEnum.ERROR.throwEcxeption();
        }

        oauthLoginProcessService.checkKaptcha(userAuthLogin);

        UserAuthLoginVO auth2AccessToken = oauth2LoginAuthorizationService.password(userAuthLogin);

        return CommonResponse.responseOf(convertUserAuthLoginResponse(auth2AccessToken));
    }


    @DeleteMapping("/logout")
    @ApiOperation("安全退出")
    @ApiImplicitParam(name = TokenUtil.HEADER_KEY, value = "token", required = true, dataType = "string", paramType = "header")
    public CommonResponse logout(HttpServletRequest request) {

        String headToken = request.getHeader(TokenUtil.HEADER_KEY);

        if (ChkUtil.isEmpty(headToken)) {
            return CommonResponse.responseOf(ErrorCodeEnum.PARAMS_MISSING);
        }

        if (headToken.contains(TokenUtil.HEADER_PREFIX_BEARER)) {
            headToken = headToken.replace(TokenUtil.HEADER_PREFIX_BEARER, "").replace(" ", "");
            if (consumerTokenServices.revokeToken(headToken)) {
                //  TokenUserInfo userInfo = JwtTokenUtil.getUser();
                //   redisUtil.del(userInfo.getUsertype() + "|" + userInfo.getUsername());
                return CommonResponse.isSuccess();
            }
        }
        return CommonResponse.responseOf(ErrorCodeEnum.ERROR.getCode(),ErrorCodeEnum.ERROR.getMessage());

    }

    @GetMapping("getInfo")
    @ApiOperation(value = "用户详情")
    @UserPermissions
    @Logger( detail= "获取用户信息{{userId}} 信息")
    public CommonResponse userInfo(@UserAuthToken String userId) {

        TokenUserInfo userInfo = JwtTokenUtil.tokenUserInfo();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return CommonResponse.responseOf(authentication);
    }

    private UserAuthLoginResponse convertUserAuthLoginResponse(UserAuthLoginVO source) {
        UserAuthLoginResponse target = new UserAuthLoginResponse();
        target.setAccess_token(source.getAccess_token());
        target.setExpires_in(source.getExpires_in());
        target.setRoleId(source.getRoleId());
        target.setRoleName(source.getRoleName());
        target.setUsertype(source.getUsertype());
        target.setSource(source.getSource());
        target.setUserId(source.getUserId());
        target.setEmail(source.getEmail());
        target.setUsername(source.getUsername());
        target.setRealname(source.getRealname());
        target.setChannelCode(source.getChannelCode());
        target.setLastLoginTime(source.getLastLoginTime());
        target.setAuthStatus(source.getAuthStatus());
        target.setMobileStatus(source.getMobileStatus());
        target.setStatus(source.getStatus());
        return target;
    }

}
