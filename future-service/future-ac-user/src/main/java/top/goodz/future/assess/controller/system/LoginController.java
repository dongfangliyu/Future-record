package top.goodz.future.assess.controller.system;

import org.springframework.web.bind.annotation.*;
import top.goodz.future.assess.controller.model.request.LoginAuthBean;
import top.goodz.future.response.CommonResponse;

/**
 * @Description 登录相关
 * @Author Yajun.Zhang
 * @Date 2020/8/8 17:00
 */
@RestController
public class LoginController {


    @RequestMapping(value = "/login/auth", method = RequestMethod.POST)
    public CommonResponse auth(@RequestBody LoginAuthBean request) {
        System.out.println("par--------------" + request);

        return CommonResponse.isSuccess();
    }
}
