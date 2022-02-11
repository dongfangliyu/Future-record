
package top.goodz.future.domain.config.support;

import org.springblade.core.tool.utils.Func;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import top.goodz.future.domain.model.FutureUserDetails;
import top.goodz.future.domain.utils.TokenUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt返回参数增强
 *
 * @author zhangyajun
 */
public class JwtTokenEnhancer implements TokenEnhancer {
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		FutureUserDetails principal = (FutureUserDetails) authentication.getUserAuthentication().getPrincipal();
		Map<String, Object> info = new HashMap<>(16);
//		info.put(TokenUtil.CHANNEL_CODE, TokenUtil.getClientIdFromHeader());
		info.put(TokenUtil.USER_ID, Func.toStr(principal.getUserId()));
		info.put(TokenUtil.USER_NAME, principal.getUsername());
		info.put(TokenUtil.REAL_NAME, Func.toStr(principal.getRealname()));
		info.put(TokenUtil.EMAIL, Func.toStr(principal.getEmail()));
		info.put(TokenUtil.USERTYPE, Func.toStr(principal.getUsertype()));
		info.put(TokenUtil.SOURCE, Func.toStr(principal.getSource()));
		info.put(TokenUtil.ROLE_ID, Func.toStr(principal.getRoleId()));
		info.put(TokenUtil.ROLE_NAME, principal.getRoleName());
		info.put(TokenUtil.CHANNEL_CODE, principal.getChannelCode());
		info.put(TokenUtil.PERMS, principal.getPerms());
		info.put("lastLoginTime", principal.getLastLoginTime());
		info.put("authStatus",principal.getAuthStatus());
		info.put("mobileStatus",principal.getMobileStatus());
		info.put("status",principal.getStatus());
//		info.put(TokenUtil.DEPT_ID, Func.toStr(principal.getDeptId()));
//		info.put(TokenUtil.TENANT_ID, principal.getTenantId());
//		info.put(TokenUtil.ACCOUNT, principal.getAccount());
//		info.put(TokenUtil.NICK_NAME, principal.getName());
//		info.put(TokenUtil.AVATAR, principal.getAvatar());
//		info.put(TokenUtil.LICENSE, TokenUtil.LICENSE_NAME);
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}
}
