package top.goodz.future.assess.controller.user;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.goodz.future.assess.controller.model.request.user.RegisterRequest;
import top.goodz.future.domian.RegisterUserService;
import top.goodz.future.domian.model.user.UserEntity;
import top.goodz.future.response.CommonResponse;

/**
 * @Description 注册 api
 * @Author Yajun.Zhang
 * @Date 2020/8/10 22:10
 */

@RestController
@Api(tags = "用户注册功能api")
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private RegisterUserService registerUserService;

    @PostMapping("/register")
    public CommonResponse register(@RequestBody RegisterRequest  registerRequest) {

        registerUserService.register(convert2UserEntity(registerRequest));

        return CommonResponse.isSuccess();
    }



    private UserEntity convert2UserEntity(RegisterRequest registerRequest) {

        UserEntity entity = new UserEntity();

        entity.setAccountName(registerRequest.getAccountName());
        entity.setPassword(registerRequest.getPassword());
        entity.setReferees(registerRequest.getReferees());
        return entity;
    }
}
