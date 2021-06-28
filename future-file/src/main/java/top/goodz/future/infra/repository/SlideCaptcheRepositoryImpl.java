package top.goodz.future.infra.repository;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
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


    private static final String SLIDE_PREFIX = "captchaAuth_";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void save(SlideAuthEntity entity) {

        SlideAuthEntity authEntity = new SlideAuthEntity();
        authEntity.setX(entity.getX());
        authEntity.setY(entity.getY());
        authEntity.setId(entity.getId());

        String json = JSON.toJSONString(authEntity);
        redisTemplate.opsForValue().set(getKey(authEntity.getId()), json);

        long millis = entity.getExpireTimestemp() - System.currentTimeMillis();

        redisTemplate.expire(getKey(authEntity.getId()), millis, TimeUnit.MILLISECONDS);

    }

    private String getKey(String uuidOne) {
       return SLIDE_PREFIX+ uuidOne;
    }

    @Override
    public SlideAuthEntity load(String authId) {
        String s = (String) redisTemplate.opsForValue().get(getKey(authId));

        if (null == s) {
            return null;
        }

        return JSON.parseObject(s, SlideAuthEntity.class);
    }

    @Override
    public void updateAuthStatus(String authId, String code) {
        SlideAuthEntity load = load(authId);

        if (null == load) {
            return;
        }

        load.setStatus(code);

        String string = JSON.toJSONString(load);

        redisTemplate.opsForValue().set(getKey(authId), string);
    }

    @Override
    public void updateExpireTime(String authId, long l) {

        SlideAuthEntity load = load(authId);

        if (null == load) {
            return;
        }

        load.setExpireTimestemp(l);

        String string = JSON.toJSONString(load);
        redisTemplate.opsForValue().set(getKey(authId), string);
        redisTemplate.expire(getKey(authId), l, TimeUnit.MILLISECONDS);

    }

    @Override
    public void deleteAuthEntity(String authId) {

    }
}
