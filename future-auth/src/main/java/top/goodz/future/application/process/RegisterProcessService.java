package top.goodz.future.application.process;

import org.springframework.stereotype.Service;
import top.goodz.future.domain.service.register.RegisterService;

import javax.annotation.Resource;
import java.util.Map;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2020/6/7 22:120
 */

@Service
public class RegisterProcessService {

    @Resource
    private RegisterService  registerService;

    public void setRedisKey(Map map) {

        registerService.setRedisKey(map);
    }
}
