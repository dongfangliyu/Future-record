package top.goodz.future.domian.impl.dubbo;

import org.apache.dubbo.config.annotation.DubboService;
import top.goodz.future.service.dubbo.SecurityVerificationRpcService;


@DubboService
public class SecurityVerificationRpcServiceImpl  implements SecurityVerificationRpcService {
    @Override
    public String createSecurity(String securityVO) {
        return "zhangyajun   i love  you " + securityVO;
    }
}
