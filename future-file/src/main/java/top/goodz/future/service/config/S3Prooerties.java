package top.goodz.future.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author zhangyajun
 *  * @createTime： 2020-4-24$ 12:00$ 
 *  * @version： 1.0
 *  
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class S3Prooerties {

    private String endpoint;

    private String accessKey;

    private  String secretKey;


}
