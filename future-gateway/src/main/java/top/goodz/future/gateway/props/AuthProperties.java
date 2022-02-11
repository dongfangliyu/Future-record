
package top.goodz.future.gateway.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限过滤
 *
 * @author zhangyajun
 */
@Data
@RefreshScope
@Component
@ConfigurationProperties("future.secure")
public class AuthProperties {

    /**
     * 放行API集合
     */
    private List<String> skipUrlList = new ArrayList<>();


}
