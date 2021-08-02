package top.goodz.future.domian.model.secuiity;

import lombok.Data;

/**
 * @ClassName: SecurityCheckVO
 * @Desc: todo
 * @Author: YaJun.Zhang
 * Date: 2021/8/1 16:59
 **/

@Data
public class SecurityCheckVO {

    private String securityNo;

    private  String emailAuthNo;

    private String smsAuthNo;

    private String emailCode;

    private String smsCode;
}
