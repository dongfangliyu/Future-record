package top.goodz.future.domian.model.secuiity;

import lombok.Data;

/**
 * @Description SecurityEntity
 * @Author Yajun.Zhang
 * @Date 2021/7/11 15:09
 */

@Data
public class SecurityEntity {

    private  String emailAuthNo;

    private String smsAuthNo;

    private String securityNo;

    private String status;


}
