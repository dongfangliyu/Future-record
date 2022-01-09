package top.goodz.future;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("top.goodz.future.*.mapper")
@EnableFeignClients
public class FutureAcTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(FutureAcTaskApplication.class, args);
    }

}
