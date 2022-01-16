package top.goodz.future.domian.impl.dubbo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import top.goodz.future.domian.impl.rabbitmq.RabbitMqMessageUtil;
import top.goodz.future.service.dubbo.SecurityVerificationRpcService;
import top.goodz.future.utils.random.RandomUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Slf4j
@DubboService
public class SecurityVerificationRpcServiceImpl implements SecurityVerificationRpcService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public String createSecurity(String securityVO) {

        RabbitMqMessageUtil rabbitMqMessageUtil = new RabbitMqMessageUtil(rabbitTemplate);

        HashMap<Object, Object> hashMap = Maps.newHashMap();
        hashMap.put("vsersion", RandomUtil.randomCharString(6));
        hashMap.put("maessage", securityVO);

        if (securityVO.contains("topic")) {
            hashMap.put("name", "Topic" + UUID.randomUUID());
            rabbitMqMessageUtil.sendTopicCompetitor(JSON.toJSONString(hashMap));

        }

        if (securityVO.contains("direct")){
            hashMap.put("name", "Direct" + UUID.randomUUID());
            rabbitMqMessageUtil.sendDirectCompetitor(JSON.toJSONString(hashMap));
        }

        if (securityVO.contains("fanout")){
            hashMap.put("name", UUID.randomUUID());
            rabbitMqMessageUtil.sendFanoutCompetitor(JSON.toJSONString(hashMap));
        }
        if (securityVO.contains("delay")){
            hashMap.put("name","delay" + UUID.randomUUID());
            rabbitMqMessageUtil.sendDelayedQueue(JSON.toJSONString(hashMap));
        }

        return securityVO;

    }

    @Override
    public String createAbbitmqMessage(String securityVO) {
        return null;
    }


}
