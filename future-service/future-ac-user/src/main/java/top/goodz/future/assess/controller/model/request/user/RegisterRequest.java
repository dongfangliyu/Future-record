package top.goodz.future.assess.controller.model.request.user;

import lombok.Data;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2021/7/4 20:29
 */

@Data
public class RegisterRequest {

    private String accountName;

    private String password;

    private String confirmPassWord;

    private String referees;

    private String  code;
}
