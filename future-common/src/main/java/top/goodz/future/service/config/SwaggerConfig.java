package top.goodz.future.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description swagger 配置
 * @Author Yajun.Zhang
 * @Date 2020/5/2 14:51
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiMinio() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("minio 接口api")
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("top.goodz.future.controller"))
                .paths(PathSelectors.regex("/api.*"))
                //.paths(PathSelectors.regex("/"))
                .build();
    }

    @Bean
    public Docket apiSmsServer() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("email/sms服务api")
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("top.goodz.future.controller"))
                .paths(PathSelectors.regex("/send.*"))
                //.paths(PathSelectors.regex("/"))
                .build();
    }

    @Bean
    public Docket apiServer() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(" service api")
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("top.goodz.future.assess.controller"))
                .paths(PathSelectors.any())
                //.paths(PathSelectors.regex("/"))
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("future-record 在线文档")
                .description("future-record在线文档开发API")
                //服务条款网
                .termsOfServiceUrl("git@gitlab.com:zhangyajun/future-record.git")
                //版本号
                .version("1.0")
                .contact(new Contact("future-record", "git@gitlab.com:zhangyajun/future-record.git", "dongfangliyu@gmail.com"))
                .build();
    }

}
