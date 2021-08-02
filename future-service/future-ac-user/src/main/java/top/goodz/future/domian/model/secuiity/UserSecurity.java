package top.goodz.future.domian.model.secuiity;

import lombok.Data;

import java.util.Date;

/**
 * @Description SecurityEntity
 * @Author Yajun.Zhang
 * @Date 2021/7/11 15:09
 */

@Data
public class UserSecurity {

    private int id;

    private String userNo;

    private String emailAuthNo;

    private String smsAuthNo;

    private String securityNo;

    private int status;

    private Date sysCreateTime;

    private Date sysUpdateTime;

}
