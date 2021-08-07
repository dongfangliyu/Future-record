package top.goodz.future.domian.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.goodz.future.domian.SecurityVerificationService;
import top.goodz.future.domian.model.secuiity.SecurityCheckVO;
import top.goodz.future.domian.model.secuiity.SecurityVO;
import top.goodz.future.domian.model.secuiity.UserSecurity;

/**
 * @Description SecurityVerificationServiceImpl
 * @Author Yajun.Zhang
 * @Date 2021/7/11 15:57
 */


@Service("securityVerificationSmsServiceImpl")
public  abstract class SecurityVerificationSmsServiceImpl implements SecurityVerificationService {


    @Override
    public String createSecurity(SecurityVO securityVO) {
        return null;
    }

    @Override
    public UserSecurity load(String securityNo) {
        return null;
    }

    @Override
    public boolean check(SecurityCheckVO securityCheckVO) {
        if (StringUtils.isEmpty(securityCheckVO.getSmsAuthNo())) {
            return false;
        }

        return true;
    }

    @Override
    public void updateStatus(UserSecurity entity) {

    }


}
