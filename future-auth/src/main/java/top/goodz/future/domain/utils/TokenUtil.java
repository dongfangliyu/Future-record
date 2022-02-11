
package top.goodz.future.domain.utils;

import lombok.SneakyThrows;
import org.springblade.core.tool.utils.Charsets;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.WebUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import top.goodz.future.constants.sercuity.TokenConstant;
import top.goodz.future.constants.sercuity.TokenUserInfo;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;

/**
 * 认证工具类
 *
 * @author zhangyajun
 */
public class TokenUtil {

	public final static String AVATAR = TokenConstant.AVATAR;
	public final static String ACCOUNT = TokenConstant.ACCOUNT;
	public final static String USER_ID = TokenUserInfo.USER_ID;
	public final static String USER_NAME = TokenUserInfo.USER_NAME;
	public final static String REAL_NAME = TokenUserInfo.REAL_NAME;
	public final static String EMAIL = TokenUserInfo.EMAIL;
	public final static String USERTYPE = TokenUserInfo.USER_TYPE;
	public final static String SOURCE = TokenUserInfo.SOURCE;
	public final static String CHANNEL_CODE = TokenUserInfo.CHANNEL_CODE;
//	public final static String DEPT_ID = TokenConstant.DEPT_ID;
	public final static String ROLE_ID = TokenUserInfo.ROLE_ID;
	public final static String ROLE_NAME = TokenUserInfo.ROLE_NAME;
	public final static String PERMS = TokenUserInfo.PERMS;
//	public final static String TENANT_ID = TokenConstant.TENANT_ID;
//	public final static String CLIENT_ID = TokenConstant.CLIENT_ID;
//	public final static String LICENSE = TokenConstant.LICENSE;
//	public final static String LICENSE_NAME = TokenConstant.LICENSE_NAME;


	public final static String USER_CODE_HEADER_KEY = "User-Code";
	public final static String DEFAULT_USER_CODE = "user";
	public final static String USER_NOT_FOUND = "用户名或密码错误";
	public final static String USER_ROLE_NOT_FOUND = "用户角色错误";
	public final static String HEADER_KEY = "Authorization";
	public final static String HEADER_PREFIX = "Basic ";
	public final static String HEADER_PREFIX_BEARER = "Bearer ";
	public final static String DEFAULT_AVATAR = "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png";

	/**
	 * 解码
	 */
	@SneakyThrows
	public static String[] extractAndDecodeHeader() {
		String header = WebUtil.getRequest().getHeader(TokenUtil.HEADER_KEY);
		if (header == null || !header.startsWith(TokenUtil.HEADER_PREFIX)) {
			throw new UnapprovedClientAuthenticationException("请求头中无client信息");
		}

		byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);

		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		} catch (IllegalArgumentException var7) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, StandardCharsets.UTF_8);
		int index = token.indexOf(StringPool.COLON);
		if (index == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		} else {
			return new String[]{token.substring(0, index), token.substring(index + 1)};
		}
	}

	/**
	 * 获取请求头中的客户端id
	 */
	public static String getClientIdFromHeader() {
		String[] tokens = extractAndDecodeHeader();
		assert tokens.length == 2;
		return tokens[0];
	}

	/**
	 * 获取token过期时间(次日凌晨3点)
	 *
	 * @return expire
	 */
	public static int getTokenValiditySecond() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
	}

	/**
	 * 获取refreshToken过期时间
	 *
	 * @return expire
	 */
	public static int getRefreshTokenValiditySeconds() {
		return 60 * 60 * 24 * 15;
	}

}
