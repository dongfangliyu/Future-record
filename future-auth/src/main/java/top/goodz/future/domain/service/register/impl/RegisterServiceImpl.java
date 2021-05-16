package top.goodz.future.domain.service.register.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.goodz.future.domain.repository.register.RegisterRepository;
import top.goodz.future.domain.service.register.RegisterService;

import javax.annotation.Resource;
import java.util.Map;
import java.util.TimerTask;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/6/7 22:17
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterServiceImpl.class);
    @Resource
    private RegisterRepository registerRepository;

    @Override
    public void setRedisKey(Map map) {

        LOGGER.info("register test redis set  key {}", map);
        registerRepository.setRedisKey(map);
    }
}
