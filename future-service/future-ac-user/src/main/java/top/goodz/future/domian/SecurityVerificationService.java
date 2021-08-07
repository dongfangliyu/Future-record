package top.goodz.future.domian;

import top.goodz.future.domian.model.secuiity.SecurityCheckVO;
import top.goodz.future.domian.model.secuiity.SecurityVO;
import top.goodz.future.domian.model.secuiity.UserSecurity;

/**
 * @Description SecurityVerificationService
 * @Author Yajun.Zhang
 * @Date 2021/7/11 12:54
 */

public abstract class SecurityVerificationService {

    public abstract String createSecurity(SecurityVO securityVO);

    public abstract UserSecurity load(String securityNo);

    public abstract boolean check(SecurityCheckVO buildCheckSecurityEmailVO);

    public abstract void update(UserSecurity entity);

    public abstract void sendEmailCode(SecurityVO securityVO);
}
