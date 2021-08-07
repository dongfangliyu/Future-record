package top.goodz.future.domian;

import top.goodz.future.domian.model.secuiity.SecurityCheckVO;
import top.goodz.future.domian.model.secuiity.SecurityVO;
import top.goodz.future.domian.model.secuiity.UserSecurity;

/**
 * @Description SecurityVerificationService
 * @Author Yajun.Zhang
 * @Date 2021/7/11 12:54
 */

public interface SecurityVerificationService {

   String  createSecurity(SecurityVO  securityVO);

   UserSecurity load(String securityNo);

   boolean check(SecurityCheckVO buildCheckSecurityEmailVO);

   void update(UserSecurity entity);

   void sendEmailCode(SecurityVO securityVO);
}
