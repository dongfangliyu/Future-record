package top.goodz.future.application.process.model;

import lombok.Data;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/9/6 0:37
 */
@Data
public class SysLoginRequest {


    private String userName;

    private String password;

    private String validateCode;

    private Boolean rememberMe;

}
