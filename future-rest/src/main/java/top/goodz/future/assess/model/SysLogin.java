package top.goodz.future.assess.model;

import lombok.Data;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/9/6 0:30
 */
@Data
public class SysLogin {


    private String userName;

    private String password;

    private String validateCode;

    private Boolean rememberMe;
}
