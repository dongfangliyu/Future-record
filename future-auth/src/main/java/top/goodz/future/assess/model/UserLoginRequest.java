package top.goodz.future.assess.model;

import lombok.Data;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2021/6/18 20:04
 */
@Data
public class UserLoginRequest {

    private String userAccount;

    private String userPassword;
}
