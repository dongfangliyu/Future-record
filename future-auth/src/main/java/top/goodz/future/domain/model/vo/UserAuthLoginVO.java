package top.goodz.future.domain.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserAuthLoginVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String access_token;
	
	private String expires_in;
	
	private String roleId;
	
	private String roleName;
	
	private String usertype;
	
	private String source;
	
	private String userId;
	
	private String email;
	
	private String username;
	
	private String realname;
	
	private String channelCode;

	private Date lastLoginTime;

	private Integer authStatus;

	private Integer mobileStatus;

	private Integer status;
}
