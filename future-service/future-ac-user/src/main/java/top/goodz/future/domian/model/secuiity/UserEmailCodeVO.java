package top.goodz.future.domian.model.secuiity;


import lombok.Data;

import java.util.Date;

/**
 * @ClassName: UserEmailCodeVO
 * @Desc: todo
 * @Author: YaJun.Zhang
 * Date: 2021/7/31 17:28
 **/
@Data
public class UserEmailCodeVO {

    private int id;
    private String emailAuthNo;
    private String emailCode;
    private long expireTime;
    private Date sysCreateTime;

    private Date sysUpdateTime;
}
