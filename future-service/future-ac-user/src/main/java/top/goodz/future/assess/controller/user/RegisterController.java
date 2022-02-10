package top.goodz.future.assess.controller.user;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.goodz.future.application.process.RegisterUserProcessService;
import top.goodz.future.assess.controller.model.request.user.RegisterActiveRequest;
import top.goodz.future.assess.controller.model.request.user.RegisterRequest;
import top.goodz.future.domian.model.user.RegisterActiveVO;
import top.goodz.future.domian.model.user.SysUserEntity;
import top.goodz.future.response.CommonResponse;

/**
 * @Description 注册 api
 * @Author Yajun.Zhang
 * @Date 2020/8/10 22:10
 */

@RestController
@Api(tags = "用户注册功能api")
@RequestMapping("/api/user")
public class RegisterController {

    @Autowired
    private RegisterUserProcessService registerUserProcessService;

    @PostMapping("/register")
    public CommonResponse register(@RequestBody RegisterRequest  registerRequest) {

        String securityNo = registerUserProcessService.register(convert2UserEntity(registerRequest));

        return CommonResponse.responseOf(securityNo);
    }

    @PostMapping("/register/active")
    public CommonResponse active(@RequestBody RegisterActiveRequest registerActiveRequest) {

        registerUserProcessService.active(convert2RegisterActiveVO(registerActiveRequest));

        return CommonResponse.isSuccess();
    }

    private RegisterActiveVO convert2RegisterActiveVO(RegisterActiveRequest registerActiveRequest) {
        RegisterActiveVO activeVO = new RegisterActiveVO();
        activeVO.setSecurityNo(registerActiveRequest.getSecurityNo());
        activeVO.setCode(registerActiveRequest.getCode());
        return activeVO;
    }


    private SysUserEntity convert2UserEntity(RegisterRequest registerRequest) {

        SysUserEntity entity = new SysUserEntity();

        entity.setAccountName(registerRequest.getAccountName());
        entity.setPassWord(registerRequest.getPassWord());
        entity.setReferees(registerRequest.getReferees());
        return entity;
    }
}
