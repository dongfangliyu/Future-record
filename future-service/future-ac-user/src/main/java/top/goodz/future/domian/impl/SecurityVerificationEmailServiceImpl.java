package top.goodz.future.domian.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.goodz.future.constants.FutureConstant;
import top.goodz.future.domian.SecurityVerificationService;
import top.goodz.future.domian.facade.EmailValidationFacade;
import top.goodz.future.domian.model.enums.SecurityStatusEnum;
import top.goodz.future.domian.model.secuiity.*;
import top.goodz.future.domian.repository.SecurityVerificationRepository;
import top.goodz.future.domian.repository.UserEmailSendCodeRepository;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.infra.feigin.model.FutureEmailData;
import top.goodz.future.infra.feigin.model.FutureEmailRequest;
import top.goodz.future.utils.random.RandomUtil;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Description SecurityVerificationServiceImpl
 * @Author Yajun.Zhang
 * @Date 2021/7/11 15:57
 */


@Service("securityVerificationEmailServiceImpl")
public class SecurityVerificationEmailServiceImpl extends SecurityVerificationService {

    @Resource
    private EmailValidationFacade emailValidationFacade;

    @Autowired
    private SecurityVerificationRepository securityVerificationRepository;

    @Autowired
    private UserEmailSendCodeRepository userEmailSendCodeRepository;

    @Override
    public String createSecurity(SecurityVO securityVO) {

        UserSecurity securityEntity = buildSecurityProcess(securityVO);

        securityVerificationRepository.save(securityEntity);

        return securityEntity.getSecurityNo();

    }

    @Override
    public UserSecurity load(String securityNo) {
        return securityVerificationRepository.load(securityNo);
    }

    @Override
    public boolean check(SecurityCheckVO securityCheckVO) {

        if (StringUtils.isEmpty(securityCheckVO.getEmailAuthNo())) {
            return false;
        }

        UserEmailCodeVO userEmailCodeVO = userEmailSendCodeRepository.load(securityCheckVO.getEmailAuthNo());

        if (!securityCheckVO.getEmailAuthNo().equals(userEmailCodeVO.getEmailAuthNo())) {
            ErrorCodeEnum.SECURITY_FAILED.throwEcxeption();
        }

        if (System.currentTimeMillis() > userEmailCodeVO.getExpireTime()) {
            ErrorCodeEnum.EMAIL_CODE_EXPIRE.throwEcxeption();
        }

        if (!userEmailCodeVO.getEmailCode().equals(securityCheckVO.getEmailCode())) {
            ErrorCodeEnum.CODE_ERROR.throwEcxeption();
        }

        return true;
    }

    @Override
    public void update(UserSecurity entity) {
        securityVerificationRepository.update(entity);
    }

    @Override
    public void sendEmailCode(SecurityVO securityVO) {

        UserSecurity userSecurity = securityVerificationRepository.load(securityVO.getSecurityNo());

        UserEmailCodeVO emailCodeVO = userEmailSendCodeRepository.load(userSecurity.getEmailAuthNo());

        securityVO.setEmail(emailCodeVO.getToSend());

        FutureEmailRequest emailRequest = buildSendEmailData(securityVO);

        UserEmailCodeVO userEmailCodeVO = sendEmailCode(emailRequest);

        userEmailSendCodeRepository.save(userEmailCodeVO);
    }


    private UserSecurity buildSecurityProcess(SecurityVO securityVO) {

        UserSecurity securityEntity = new UserSecurity();
        securityEntity.setSecurityNo("SE" + UUID.randomUUID().toString().replace("-", ""));
        securityEntity.setStatus(SecurityStatusEnum.INIT.getCode());
        securityEntity.setUserNo(securityVO.getUserNo());

        if (securityVO.isEmailFlag()) {

            FutureEmailRequest emailRequest = buildSendEmailData(securityVO);

            if (securityVO.isSendEmailFlag()) {
                return null;
            }

            UserEmailCodeVO userEmailCodeVO = sendEmailCode(emailRequest);

            userEmailSendCodeRepository.save(userEmailCodeVO);

            securityEntity.setEmailAuthNo(userEmailCodeVO.getEmailAuthNo());
        }
        if (securityVO.isSmsFlag()) {
            // TODO: 2021/7/11
        }

        return securityEntity;

    }

    private UserEmailCodeVO sendEmailCode(FutureEmailRequest emailRequest) {

        UserEmailCodeVO codeVO = new UserEmailCodeVO();

        codeVO.setEmailAuthNo(emailRequest.getData().getServiceId());
        String code = RandomUtil.randomString(6);
        emailRequest.getData().setContent(String.format(emailRequest.getData().getContent(), code));
        codeVO.setEmailCode(code);
        codeVO.setToSend(emailRequest.getData().getToMail());
        codeVO.setExpireTime(System.currentTimeMillis() + 300);

        emailValidationFacade.send(emailRequest);

        return codeVO;

    }

    private FutureEmailRequest buildSendEmailData(SecurityVO securityVO) {

        FutureEmailRequest request = new FutureEmailRequest();
        request.setChannelsName("SecurityVerificationServiceImpl");
        request.setCreator("devlops");
        request.setEmailId(RandomUtil.randomString(9));

        FutureEmailData emailData = new FutureEmailData();
        emailData.setSubject(FutureConstant.FUTURE_AC_NAME);
        emailData.setToMail(securityVO.getEmail());
        emailData.setServiceId("EM" + UUID.randomUUID().toString().replace("-", ""));
        emailData.setContent("<hr style = 'font-size:2px; color:#10fa81;' /> <span style = 'font-size:18px;'><br/>注册邮箱验证码： %s  <br/>邮箱验证在5分钟内有效，请在安全环境下使用，防止泄露！</span><br/><br/><hr style = 'font-size:2px; color:#10fa81;' /><br/><br/><span style = 'font-size:16px;'>此邮件为 future 项目开发阶段测试使用！使用过程中有任何问题，请联系email:zhangyajun_nb.163.com</span>");
        request.setData(emailData);

        return request;
    }
}
