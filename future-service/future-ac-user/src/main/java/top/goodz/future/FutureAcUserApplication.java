package top.goodz.future;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
@ComponentScan(basePackages = {"top.goodz"})
public class FutureAcUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(FutureAcUserApplication.class, args);
    }

}
