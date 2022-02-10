package top.goodz.future.domain.constant;

/**
 * ouath公共配置变量
 * @author zhuliang
 */
public class ConstantsOuath {
	
	/**
	 * 授权方式 client
	 */
	public static final String OAUTH_GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

	/**
	 * 授权方式 密码
	 */
	public static final String OAUTH_GRANT_TYPE_PWD = "password";
	public static final String AUTHORIZATION_CODE = "authorization_code";
	public static final String CLIENT_CREDENTIALS = "client_credentials";
	public static final String IMPLICIT = "implicit";

	/**
	 * 客户端
	 */
	public static final String OAUTH_WITHCLIENT = "client_future";

	/**
	 * 限制客户端的访问范围
	 */
	public static final String OAUTH_SCOPES = "all";

	/**
	 * 客户端可以使用的权限
	 */
	public static final String OAUTH_AUTHORITIES = "oauth2";

	/**
	 * 客户端安全码
	 */
	public static final String OAUTH_SECRET = "future_eyJhbGciOiJIUzUxMiJ9";

	/**
	 * token有效时间
	 */
	public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 86400;

	/**
	 * refresh token 有效时间
	 */
	public static final int REFRESH_TOKEN_VALIDITY_SECONDS = 43200;

	/**
	 * oauth token
	 */
	public static final String OAUTH_TOKEN_KEY = "Authorization";

	/**
	 * 返回token 在header里提交时必须加这个开头单词
	 */
	public static final String ACCESS_TOKEN_START_PORTION = "Bearer";


	/**
	 * 资源标识
	 */
	public static final String FUTURE_RESOURCE_ID = "api";



}
