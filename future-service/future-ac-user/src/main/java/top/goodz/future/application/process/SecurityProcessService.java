package top.goodz.future.application.process;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.goodz.future.domian.RegisterUserService;
import top.goodz.future.domian.SecurityVerificationService;
import top.goodz.future.domian.model.enums.SecurityStatusEnum;
import top.goodz.future.domian.model.enums.UserStatusEnum;
import top.goodz.future.domian.model.secuiity.SecurityCheckVO;
import top.goodz.future.domian.model.secuiity.SecurityVO;
import top.goodz.future.domian.model.secuiity.SendEmailCodeVO;
import top.goodz.future.domian.model.secuiity.UserSecurity;
import top.goodz.future.domian.model.user.RegisterActiveVO;
import top.goodz.future.domian.model.user.UserEntity;
import top.goodz.future.enums.ErrorCodeEnum;

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
