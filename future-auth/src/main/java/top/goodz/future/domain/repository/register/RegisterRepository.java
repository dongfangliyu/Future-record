package top.goodz.future.domain.repository.register;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.exception.CommonException;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/6/7 22:19
 */
@Repository
public class RegisterRepository {

    @Resource
    private RedisTemplate redisTemplate;

    public void setRedisKey(Map map) {
        if (map.isEmpty()) {
            ErrorCodeEnum.ERROR.throwEcxeption();
        }
        String zyj = (String) map.get("zyj");
        redisTemplate.opsForValue().set("zyj", zyj);
    }
}
