
package top.goodz.future.domain.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import top.goodz.future.domain.config.support.JwtTokenEnhancer;
import top.goodz.future.domain.utils.JwtUtil;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * JwtTokenStore
 *
 * jwt 扩展用来覆盖默认的token
 *
 * @author zhangyajun
 */
@Configuration

public class JwtTokenStoreConfiguration {

	/**
	 * 使用jwtTokenStore存储token
	 */
	@Bean
	@ConditionalOnProperty(prefix = "future.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
	public TokenStore jwtTokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	/**
	 * 用于生成jwt
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		SignatureAlgorithm algorithm = SignatureAlgorithm.ES256;
		byte[] bytes = DatatypeConverter.parseBase64Binary(JwtUtil.ACCESS_TOKEN_KEY);
		SecretKeySpec signingKey = new SecretKeySpec(bytes, algorithm.getJcaName());
		accessTokenConverter.setSigningKey(JwtUtil.ACCESS_TOKEN_KEY);
		return accessTokenConverter;
	}

	/**
	 * 用于扩展jwt存储信息
	 */
	@Bean
	@ConditionalOnMissingBean(name = "jwtTokenEnhancer")
	public TokenEnhancer jwtTokenEnhancer() {
		return new JwtTokenEnhancer();
	}

}
