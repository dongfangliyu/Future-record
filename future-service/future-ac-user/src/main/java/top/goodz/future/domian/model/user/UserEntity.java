package top.goodz.future.domian.model.user;

import lombok.Data;

import java.util.Date;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2021/7/4 20:29
 */

@Data
public class UserEntity {

    private int  id;

    private String accountName;

    private String passWord;

    private int type;

    private int  status;

    private String userNo;

    private String referees;

    private Date createTime;

}
