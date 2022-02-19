package top.goodz.future.jwttoken;

import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import top.goodz.future.AuthTestApplication;
import top.goodz.future.domain.model.vo.token.TokenTemplate;
import top.goodz.future.domain.utils.JwtUtil;

import java.util.Arrays;
import java.util.HashMap;

@SpringBootTest(classes = AuthTestApplication.class)
@RunWith(SpringRunner.class)
public class JwtTokenTest {



    @Test
    public void contextLoads() {

        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("userName", "zhangyjun");
        objectObjectHashMap.put("type", 1);
        objectObjectHashMap.put("role", Arrays.asList("role,admin"));

        TokenTemplate jwtToken = JwtUtil.createJwtToken(objectObjectHashMap, 15);

        /**
         * TokenTemplate(
         * accessToken=eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJmdXR1cmUtNGE2ODA1MTAtZjZmMC00ODExLWI1ZGQtZTAzMDI0ZjFjNTJhIiwic3ViIjoiemhhbmd5anVuIiwicm9sZSI6WyJyb2xlLGFkbWluIl0sInVzZXJOYW1lIjoiemhhbmd5anVuIiwidHlwZSI6MSwiaWF0IjoxNjQzMTI3NjI2LCJleHAiOjE2NDMxMjg1MjZ9.guhB0zZxLC1M7B6Xdiw4-YBkcfzL4CV0V537zDj1e3Jv3LnalqYYmeJbY4JRIMEV9cOMkRnAbt89mbBbQJibvA,
         * refreshToken=eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJmdXR1cmUtY2RhNWRjMmYtYzQxNi00YzhhLThlZDEtMTdjNmFjNmRhM2ZhIiwic3ViIjoiemhhbmd5anVuIiwiaWF0IjoxNjQzMTI3NjI2LCJleHAiOjE2NDMxMjg1MjYsInVzZXJOYW1lIjoiemhhbmd5anVuIn0.clVtD-fGwzZGfbOHi2ZAFywbRncV58HqXolPqTg4gAH_7JgEcpg9ADBqrKJ9jM1PztIX14cNIMuHluQlyqsZ5Q)
         */
        System.out.printf("token: \n" + jwtToken);
    }

    @Test
    public void paeseJetTest() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJmdXR1cmUtY2RhNWRjMmYtYzQxNi00YzhhLThlZDEtMTdjNmFjNmRhM2ZhIiwic3ViIjoiemhhbmd5anVuIiwiaWF0IjoxNjQzMTI3NjI2LCJleHAiOjE2NDMxMjg1MjYsInVzZXJOYW1lIjoiemhhbmd5anVuIn0.clVtD-fGwzZGfbOHi2ZAFywbRncV58HqXolPqTg4gAH_7JgEcpg9ADBqrKJ9jM1PztIX14cNIMuHluQlyqsZ5Q";

        Claims claims = JwtUtil.parseAccessToken(token);

        System.out.printf("claims:" + claims);
    }

    @Test
    public void passwordTest() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


        String encode = bCryptPasswordEncoder.encode("Aa123qwe!");
        System.out.printf(":" + encode);
        boolean matches = bCryptPasswordEncoder.matches("123465678","$2a$10$vv07mIozWoEfvGwHPf2XT.fTamvsh39yk9HW/j1Gw752ylbydWPAW" );

        System.out.printf("matches:" + matches);
    }

}
