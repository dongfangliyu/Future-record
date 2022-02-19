package top.goodz.future.application.process;

import org.springframework.stereotype.Service;
import top.goodz.future.assess.model.UserAuthLoginRequest;
import top.goodz.future.domain.service.register.RegisterService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description 登陆相关业务
 * @Author Yajun.Zhang
 * @Date 2020/6/7 22:120
 */

@Service
public class OauthLoginProcessService {


    public void checkKaptcha(UserAuthLoginRequest userAuthLogin) {
    }
}
