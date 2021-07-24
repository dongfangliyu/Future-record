package top.goodz.future.domian.model.secuiity;

import lombok.Data;

/**
 * @Description SecurityEntity
 * @Author Yajun.Zhang
 * @Date 2021/7/11 15:09
 */

@Data
public class SecurityVO {

    private  boolean emailFlag;

    private boolean smsFlag;

    private String userNo;

    private String email;

}
