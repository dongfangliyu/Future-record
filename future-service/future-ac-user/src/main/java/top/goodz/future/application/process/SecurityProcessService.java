package top.goodz.future.application.process;

import org.springframework.stereotype.Component;
import top.goodz.future.domian.SecurityVerificationService;
import top.goodz.future.domian.model.secuiity.SecurityVO;
import top.goodz.future.domian.model.secuiity.SendEmailCodeVO;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2021/7/11 12:15
 */

@Component
public class SecurityProcessService {


    @Resource(name = "securityVerificationEmailServiceImpl")
    private SecurityVerificationService securityVerificationService;


    public void sendEmailCode(SendEmailCodeVO sendEmailCodeVO) {
        securityVerificationService.sendEmailCode(buildSecurityProcess(sendEmailCodeVO));
    }

    private SecurityVO buildSecurityProcess(SendEmailCodeVO sendEmailCodeVO) {
        SecurityVO securityVO = new SecurityVO();
        securityVO.setEmailFlag(true);
        securityVO.setSendEmailFlag(true);
        securityVO.setSmsFlag(false);
        securityVO.setSecurityNo(sendEmailCodeVO.getSecurityNo());


        return securityVO;
    }
}
