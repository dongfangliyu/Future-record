package top.goodz.future.service.dubbo;


/**
 * @Description SecurityVerificationService
 * @Author Yajun.Zhang
 * @Date 2021/7/11 12:54
 */

public interface SecurityVerificationRpcService {

    public  String createSecurity(String securityVO);

    public  String createAbbitmqMessage(String securityVO);

}
