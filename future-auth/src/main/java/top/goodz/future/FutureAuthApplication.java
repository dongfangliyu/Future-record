package top.goodz.future;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"org.springblade","top.goodz"})
public class FutureAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(FutureAuthApplication.class, args);
    }

}
