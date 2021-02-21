package top.goodz.future.request;

import lombok.Data;

/**
 *  @Description 邮箱基本信息
 *  @Author Yajun.Zhang
 *  @Date 2020/5/4 13:19
 */
@Data
public class FutureEmailData {


    private String host;

    private String username;

    private String password;


    private String port;
}
