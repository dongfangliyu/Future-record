package top.goodz.future.controller;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import top.goodz.future.feigin.IFutureFileClient;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author 1$
 *  * @createTime： 1$ 1$ 
 *  * @version： 2.1
 *  
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private IFutureFileClient futureFileClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @org.junit.Test
    @Ignore
    public void test() {
        System.out.println("qwew");
        try {
            //   System.out.println(futureFileClient.list("zyj"));
            File file1 = new File("F:\\prijects\\future-record\\project\\pdf\\" + "测试.pdf");
            FileInputStream inputStream = new FileInputStream(file1);

            MultipartFile file = new MockMultipartFile("file" + file1.getName(), "测试.pdf", "multipart/form-data", inputStream);

            System.out.println(futureFileClient.upLoad("user", file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void setRedisKey() {
        // redisTemplate.opsForValue().set("zz","qw");
        //  System.out.println(redisTemplate.opsForValue().get("zz"));

        // redisTemplate.opsForList().leftPush("zyj", "123", 12);
        //  redisTemplate.expire("zyj", 10, TimeUnit.SECONDS);

        //   System.out.printf("" + redisTemplate.opsForList().range("zyj", 0, -1));

        A a1 = new A();
        A A2 = new B();
        B b = new B();
        System.out.printf(A2.show(b));
    }


    public static void main(String[] args) {

        String s = "a" + new String("b");

        System.out.printf(s);
    }

    class A {
        public String show(A obj) {
            return ("A and A");
        }
    }

    class B extends A {
        public String show(B obj) {
            return ("B and B");
        }

        public String show(A obj) {
            return ("B and A");
        }
    }
}
