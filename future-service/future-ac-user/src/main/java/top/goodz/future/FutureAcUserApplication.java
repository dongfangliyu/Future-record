package top.goodz.future;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan("top.goodz.future.*.dao")
@EnableDubbo
public class FutureAcUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(FutureAcUserApplication.class, args);
    }

}
