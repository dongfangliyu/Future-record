package top.goodz.future.assess.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.goodz.future.infra.annotation.ValidPassword;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "统一登录-请求参数")
public class UserAuthLogin {


    @ApiModelProperty(value = "用户名",required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    @NotEmpty(message = "密码不能为空")
    @ValidPassword
    private String password;

    @ApiModelProperty(value = "验证码uuid",required = true)
    @NotEmpty(message = "验证码uuid不能为空")
    private String uuid;

    @ApiModelProperty(value = "验证码",required = true)
    @NotEmpty(message = "验证码不能为空")
    private String kaptcha;

}
