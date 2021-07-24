package top.goodz.future.domian.impl;

import org.springframework.stereotype.Service;
import top.goodz.future.constants.FutureConstant;
import top.goodz.future.domian.SecurityVerificationService;
import top.goodz.future.domian.facade.EmailValidationFacade;
import top.goodz.future.domian.model.secuiity.SecurityEntity;
import top.goodz.future.domian.model.secuiity.SecurityVO;
import top.goodz.future.infra.feigin.model.FutureEmailData;
import top.goodz.future.infra.feigin.model.FutureEmailRequest;
import top.goodz.future.utils.random.RandomUtil;

import javax.annotation.Resource;

/**
 * @Description SecurityVerificationServiceImpl
 * @Author Yajun.Zhang
 * @Date 2021/7/11 15:57
 */


@Service
public class SecurityVerificationServiceImpl implements SecurityVerificationService {

    @Resource
    private EmailValidationFacade emailValidationFacade;

    @Override
    public void createSecurity(SecurityVO securityVO) {

        SecurityEntity securityEntity =  buildSecurityProcess(securityVO);

    }

        private SecurityEntity buildSecurityProcess(SecurityVO securityVO) {

            SecurityEntity securityEntity = new SecurityEntity();
            if (securityVO.isEmailFlag()){

                emailValidationFacade.send(buildSendEmailData(securityVO));


            }
            if (securityVO.isSmsFlag()){
                // TODO: 2021/7/11
            }

            return null;

        }

    private FutureEmailRequest buildSendEmailData(SecurityVO securityVO) {
        FutureEmailRequest request = new FutureEmailRequest();
        request.setChannelsName("SecurityVerificationServiceImpl");
        request.setCreator("devlops");
        request.setEmailId(RandomUtil.randomString(9));

        FutureEmailData emailData = new FutureEmailData();
        emailData.setSubject(FutureConstant.FUTURE_AC_NAME);
        emailData.setToMail(securityVO.getEmail());
        emailData.setServiceId(securityVO.getUserNo());
        emailData.setContent(String.format("<hr style = 'font-size:2px; color:#10fa81;' /> <span style = 'font-size:18px;'><br/>注册邮箱验证码： %s  <br/>邮箱验证在5分钟内有效，请在安全环境下使用，防止泄露！</span><br/><br/><hr style = 'font-size:2px; color:#10fa81;' /><br/><br/><span style = 'font-size:16px;'>此邮件为 future 项目开发阶段测试使用！使用过程中有任何问题，请联系email:zhangyajun_nb.163.com</span>",RandomUtil.randomString(6)));
        request.setData(emailData);

        return request;
    }
}
