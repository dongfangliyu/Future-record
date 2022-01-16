package top.goodz.future.assess.controller.system;


import org.apache.commons.codec.language.Nysiis;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.InvokerListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.service.dubbo.SecurityVerificationRpcService;

@RestController
@RequestMapping("/rpc")
public class RpcController {

    @DubboReference(filter ="userFilter" ,listener ="mytest" )
    private SecurityVerificationRpcService securityVerificationRpcService;

    @GetMapping("/test")
    public CommonResponse test(@RequestParam String name){

            String security = securityVerificationRpcService.createSecurity(name);

            return CommonResponse.responseOf(security);

    }
}
