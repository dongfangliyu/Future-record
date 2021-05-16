package top.goodz.future.service.config;

import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description minio 配置类
 * @Author Yajun.Zhang
 * @Date 2020/5/1 23:27
 */
@Configuration
@ConditionalOnClass(MinioClient.class)
public class MinioS3Configuration {

    @Bean
    @ConditionalOnMissingBean
    public MinioClient minioClient(S3Prooerties prooerties) throws Exception {
        return new MinioClient(prooerties.getEndpoint(), prooerties.getAccessKey(), prooerties.getSecretKey());
    }


}
