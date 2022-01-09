package top.goodz.future.assess.controller.system;


import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.service.dubbo.SecurityVerificationRpcService;

@RestController
@RequestMapping("/rpc")
public class RpcController {

    @DubboReference
    private SecurityVerificationRpcService securityVerificationRpcService;

    @GetMapping("/test")
    public CommonResponse test(@RequestParam String name){

        String security = securityVerificationRpcService.createSecurity(name);

        return CommonResponse.responseOf(security);
    }
}
