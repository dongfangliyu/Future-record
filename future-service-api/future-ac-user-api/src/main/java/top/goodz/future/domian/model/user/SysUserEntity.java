package top.goodz.future.domian.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2021/7/4 20:29
 */

@Data
public class SysUserEntity implements Serializable {

    private int  id;

    private String  UserName;

    private String realName;

    private String email;

    private String userSource;

    private String channelSource;

    private String accountName;

    private String passWord;

    private String lastLoginTime;

    private int userType;

    private int  status;

    private int authStatus;

    private String userNo;

    private String referees;

    private Date createTime;

    private List list = new ArrayList<String>();

}
