package top.goodz.future.assess.controller.register;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.goodz.future.application.process.RegisterProcessService;
import top.goodz.future.response.CommonResponse;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/6/6 23:39
 */
@RestController
@Api("注册相关API")
@RequestMapping("/register")
public class RegisterController {

    @Resource
    private RegisterProcessService  registerProcessService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation("注册")
    public CommonResponse register() {

        HashMap<Object, String> map = new HashMap<>();
        map.put("zyj","我是开发工程师张亚军；");
        registerProcessService.setRedisKey(map);
        return CommonResponse.isSuccess();
    }

}
