package top.goodz.future.domian.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *  @Description
 *  @Author Yajun.Zhang
 *  @Date 2020/9/6 0:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLoginRequestBean implements Serializable {


    private String userName;

    private String password;

    private String validateCode;

    private Boolean rememberMe;

}
