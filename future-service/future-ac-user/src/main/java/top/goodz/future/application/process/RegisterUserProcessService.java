package top.goodz.future.application.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.goodz.future.domian.RegisterUserService;
import top.goodz.future.domian.SecurityVerificationService;
import top.goodz.future.domian.model.secuiity.SecurityVO;
import top.goodz.future.domian.model.user.UserEntity;

import javax.naming.directory.SearchResult;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2021/7/11 12:15
 */

@Component
public class RegisterUserProcessService {

    @Autowired
    private RegisterUserService  registerUserService;

    @Autowired
    private SecurityVerificationService  securityVerificationService;

    public void register(UserEntity convert2UserEntity) {

        UserEntity userEntity = registerUserService.register(convert2UserEntity);

        //创建验证流程
        securityVerificationService.createSecurity(buildSecurity(userEntity));

    }

    private SecurityVO buildSecurity(UserEntity user) {
        SecurityVO securityVO = new SecurityVO();
        securityVO.setEmailFlag(true);
        securityVO.setSmsFlag(false);
        securityVO.setUserNo(user.getUserNo());
        securityVO.setEmail(user.getAccountName());

        return securityVO;
    }
}
