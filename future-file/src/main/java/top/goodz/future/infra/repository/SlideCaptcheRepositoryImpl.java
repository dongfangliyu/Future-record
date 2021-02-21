package top.goodz.future.infra.repository;


import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.goodz.future.service.model.request.SlideAuthEntity;
import top.goodz.future.service.repository.SlideCaptcheRepository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description 实现
 * @Author Yajun.Zhang
 * @Date 2020/6/14 16:01
 */
@Service
public class SlideCaptcheRepositoryImpl implements SlideCaptcheRepository {


    private static final String SLIDE_PREFIX = "SlideCaptchaAuth_";

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void save(SlideAuthEntity entity) {
        String json = JSON.toJSONString(entity);
        redisTemplate.opsForValue().set(SLIDE_PREFIX + entity.getId(), json);

        long millis = entity.getExpireTimestemp() - System.currentTimeMillis();

        redisTemplate.expire(SLIDE_PREFIX + entity.getId(), millis, TimeUnit.MICROSECONDS);

    }
}
