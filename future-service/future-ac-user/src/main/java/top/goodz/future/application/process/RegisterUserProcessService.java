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
import top.goodz.future.domian.model.secuiity.UserSecurity;
import top.goodz.future.domian.model.user.RegisterActiveVO;
import top.goodz.future.domian.model.user.UserEntity;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.exception.ServiceException;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2021/7/11 12:15
 */

@Component
public class RegisterUserProcessService {

    @Autowired
    private RegisterUserService registerUserService;

    @Resource(name = "securityVerificationEmailServiceImpl")
    private SecurityVerificationService securityVerificationService;

    public String register(UserEntity convert2UserEntity) {

        UserEntity userEntity = registerUserService.register(convert2UserEntity);

        //创建验证流程
        String securityNo = securityVerificationService.createSecurity(buildSecurity(userEntity));

        return securityNo;
    }

    private SecurityVO buildSecurity(UserEntity user) {
        SecurityVO securityVO = new SecurityVO();
        securityVO.setEmailFlag(true);
        securityVO.setSmsFlag(false);
        securityVO.setUserNo(user.getUserNo());
        securityVO.setEmail(user.getAccountName());
        securityVO.setSendEmailFlag(false);

        return securityVO;
    }

    public void active(RegisterActiveVO convert2RegisterActiveVO) {
        UserSecurity entity = securityVerificationService.load(convert2RegisterActiveVO.getSecurityNo());

        if (entity.getStatus() == SecurityStatusEnum.SUCCESS.getCode()) {
            throw  new ServiceException(ErrorCodeEnum.NOT_REPEAT_AUTHENTICATION);
        }


        securityVerificationService.check(buildCheckSecurityEmailVO(entity, convert2RegisterActiveVO));

        UserEntity userEntity = new UserEntity();
        userEntity.setUserNo(entity.getUserNo());
        userEntity.setStatus(UserStatusEnum.ACTIVE.getCode());
        registerUserService.active(userEntity);

        entity.setStatus(SecurityStatusEnum.SUCCESS.getCode());
        securityVerificationService.update(entity);
    }

    private SecurityCheckVO buildCheckSecurityEmailVO(UserSecurity entity, RegisterActiveVO convert2RegisterActiveVO) {

        if (StringUtils.isEmpty(entity.getEmailAuthNo()) && StringUtils.isEmpty(entity.getSmsAuthNo())) {
            ErrorCodeEnum.SECURITY_MFA_FAILED.throwEcxeption();
        }

        SecurityCheckVO checkVO = new SecurityCheckVO();
        checkVO.setEmailAuthNo(entity.getEmailAuthNo());
        checkVO.setEmailCode(convert2RegisterActiveVO.getCode());
        return checkVO;
    }
}
