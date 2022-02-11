
package top.goodz.future.domain.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

/**
 * 仲裁用户-登录信息
 * @author zhangyajun
 */
@Getter
public class FutureUserDetails extends User {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String userId;					//用户id
	private String username;				//用户名
	private String realname;				//真实姓名
	private String email;					//邮箱
	private Integer usertype;				//用户端-user， 登陆人类型1.个人 2.法人
	private String source;					//系统来源 仲裁端-arb、用户端-user
	private String channelCode;				//渠道code
	private String roleId;					//角色id
	private String roleName;				//角色名
	private String perms;					//角色名
	private Date lastLoginTime;				//最后一次登录时间
	/**
	 * 邮箱认证状态：1-已认证，2-未认证
	 */
	private Integer authStatus;
	/**
	 *手机认证状态：1-已认证，2-未认证
	 */
	private Integer mobileStatus;
	/**
	 * 状态 1-启用 2-停用
	 */
	private Integer status;

	public FutureUserDetails(String userId, String username, String realname, String email,
                             Integer usertype, String source, String channelCode, String roleId, String roleName,
                             Date lastLoginTime, Integer authStatus, Integer mobileStatus, Integer status, String password, String perms,
                             boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                             Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.username = username;
		this.realname = realname;
		this.email = email;
		this.usertype = usertype;
		this.source = source;
		this.channelCode = channelCode;
		this.roleId = roleId;
		this.roleName = roleName;
		this.lastLoginTime = lastLoginTime;
		this.authStatus = authStatus;
		this.mobileStatus = mobileStatus;
		this.status = status;
		this.perms = perms;
	}
}
