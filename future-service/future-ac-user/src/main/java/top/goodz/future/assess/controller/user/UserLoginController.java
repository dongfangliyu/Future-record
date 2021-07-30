package top.goodz.future.assess.controller.user;

import org.springframework.web.bind.annotation.*;
import top.goodz.future.assess.controller.model.request.user.UserLoginRequest;
import top.goodz.future.response.CommonResponse;

/**
 * @Description 登录相关
 * @Author Yajun.Zhang
 * @Date 2020/8/8 17:00
 */
@RestController
@RequestMapping("/api")
public class UserLoginController {


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResponse login(@RequestBody UserLoginRequest request) {
        System.out.println("par--------------" + request);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserAccount("zhangyajun");
        userLoginRequest.setUserPassword("123456");
        return CommonResponse.responseOf(userLoginRequest);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResponse info(@RequestParam("id") String id) {
        System.out.println("par--------------" + id);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        if ("zhangyajun".equals(id)) {

            userLoginRequest.setUserAccount("zhangyajun");
            userLoginRequest.setUserPassword("123456");
        }


        return CommonResponse.responseOf(userLoginRequest);
    }
}
