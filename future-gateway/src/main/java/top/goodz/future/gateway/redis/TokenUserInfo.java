package top.goodz.future.gateway.redis;

import lombok.Data;

@Data
public class TokenUserInfo {
	
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "username";
	public static final String REAL_NAME = "realname";
	public static final String EMAIL = "email";
	public static final String USER_TYPE = "usertype";
	public static final String SOURCE = "source";
	public static final String CHANNEL_CODE = "channelCode";
	public static final String ROLE_ID = "roleId";
	public static final String ROLE_NAME = "roleName";
	
	private String userId;					//用户id
	private String username;				//用户名
	private String realname;				//真实姓名
	private String email;						//邮箱
	private String usertype;				//用户端-user， 登陆人类型1.个人 2.法人
	private String source;					//系统来源 仲裁端-arb、用户端-user
	private String channelCode;			//渠道code
	private String roleId;					//角色id
	private String roleName;				//角色名
}
