package top.goodz.future.assess.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "统一登录-响应参数")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserAuthLoginResponse implements Serializable {
	
	/**
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "token",required = true)
	private String access_token;
	
	private String expires_in;
	
	@ApiModelProperty(value = "角色id",required = true)
	private String roleId;
	
	@ApiModelProperty(value = "角色名称",required = true)
	private String roleName;
	
	@ApiModelProperty(value = "用户类型，user，",required = true)
	private String usertype;
	
	@ApiModelProperty(value = "账户所属model",required = true)
	private String source;
	
	@ApiModelProperty(value = "用户ID",required = true)
	private String userId;
	
	@ApiModelProperty(value = "用户邮箱",required = true)
	private String email;
	
	@ApiModelProperty(value = "用户登录名",required = true)
	private String username;
	
	@ApiModelProperty(value = "用户真实姓名",required = true)
	private String realname;
	
	@ApiModelProperty(value = "渠道code",required = true)
	private String channelCode;

	@ApiModelProperty(value = "最后一次登录时间",required = true)
	private Date lastLoginTime;

	@ApiModelProperty(value = "邮箱认证状态：1-已认证，2-未认证",required = true)
	private Integer authStatus;

	@ApiModelProperty(value = "手机认证状态：1-已认证，2-未认证",required = true)
	private Integer mobileStatus;

	@ApiModelProperty(value = "状态 1-启用 2-停用")
	private Integer status;
}
