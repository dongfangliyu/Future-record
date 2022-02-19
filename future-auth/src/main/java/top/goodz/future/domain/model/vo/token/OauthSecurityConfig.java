package top.goodz.future.domain.model.vo.token;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@ConfigurationProperties(prefix = "future.security.oauth.login")
@Data
@Component
public class OauthSecurityConfig {

    private String accessTokenUrl;

    private String checkTokenUrl;

}
