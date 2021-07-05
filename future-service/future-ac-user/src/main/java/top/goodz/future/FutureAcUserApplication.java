package top.goodz.future;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@MapperScan("top.goodz.future.*.dao")
public class FutureAcUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(FutureAcUserApplication.class, args);
    }

}
