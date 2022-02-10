package top.goodz.future.assess.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.goodz.future.infra.annotation.ValidPassword;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "统一登录-授权码-请求参数")
public class CodeTokenLogin {

    @ApiModelProperty(value = "验证码",required = true)
    @NotEmpty(message = "验证码不能为空")
    private String code;

    @ApiModelProperty("state")
    private String state;

}
