
package top.goodz.future.domain.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Redis TokenStore
 *
 * @author zhangyajun
 */
@Configuration
@AllArgsConstructor
public class RedisTokenStoreConfiguration {
	/**
	 * redis连接工厂
	 */
	private RedisConnectionFactory redisConnectionFactory;

	/**
	 * 用于存放token
	 */
	@Bean
	@ConditionalOnProperty(prefix = "future.security.oauth2", name = "storeType", havingValue = "redis")
	public TokenStore redisTokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}
}
