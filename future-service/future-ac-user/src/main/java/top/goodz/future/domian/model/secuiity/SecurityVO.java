package top.goodz.future.domian.model.secuiity;

import lombok.Data;

import java.util.PriorityQueue;

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

    private String securityNo;

    private String email;

    private  boolean sendEmailFlag;

    private  boolean sendSmsFlag;

}
