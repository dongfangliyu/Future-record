package top.goodz.future.domian.impl.rabbitmq;

import com.rabbitmq.client.AMQP;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import top.goodz.future.domian.constant.ProcessMqConstant;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Builder
public class RabbitMqMessageUtil {


    private static RabbitTemplate rabbitTemplate;

    public RabbitMqMessageUtil(RabbitTemplate  rabbitTemplate){
         this.rabbitTemplate = rabbitTemplate;
    }

    private RabbitMqMessageUtil() {
    }

    /**
     * 信息发送到队列中
     * fanout模式，rout值失效，根据queue绑定，exchange必须相同
     *
     * @param message 用户数据
     * @author zhangyajun
     * @date 2020/5/21 0021 下午 4:04
     */
    public static void sendFanoutCompetitor(String message) {
        log.info("  future fanout ---RabbitMQ开始发送消息***");
        //correlationData消息唯一id
        CorrelationData correlationData = new CorrelationData();
        String messageId = UUID.randomUUID().toString();
        correlationData.setId(messageId);
        //设置消息确认机制
        // rabbitTemplate.setConfirmCallback(rabbitmqConfirmListener);
        //exchange
        rabbitTemplate.convertAndSend(ProcessMqConstant.FANOUT_EXCHANGE,
                //routingKey 路由key 在rabbitmq中交换机Rout Key对应的值
                "future.test.info",
                //消息体内容
                message,
                //correlationData消息唯一id
                correlationData);
        log.info("*****RabbitMQ fanout发送消息完成,消息ID为，{} ,参赛信息为 fanout competitions: {} *****", messageId, message);
    }

    public static void sendTopicCompetitor(String message) {
        log.info("  future topic  ---RabbitMQ开始发送消息***");
        //correlationData消息唯一id
        CorrelationData correlationData = new CorrelationData();
        String messageId = UUID.randomUUID().toString();
        correlationData.setId(messageId);
        //设置消息确认机制
        // rabbitTemplate.setConfirmCallback(rabbitmqConfirmListener);
        //exchange
        rabbitTemplate.convertAndSend(ProcessMqConstant.TOPIC_EXCHANGE,
                //routingKey 路由key 在rabbitmq中交换机Rout Key对应的值
                "future.topic.userInfo",
                //消息体内容
                message,
                //correlationData消息唯一id
                correlationData);
        log.info("*****RabbitMQ topic 发送消息完成,消息ID为，{} ,参赛信息为 topiccompetitions: {} *****", messageId, message);
    }


    public static void sendDirectCompetitor(String message) {
        log.info("  future direct  ---RabbitMQ开始发送消息***");
        //correlationData消息唯一id
        CorrelationData correlationData = new CorrelationData();
        String messageId = UUID.randomUUID().toString();
        correlationData.setId(messageId);



        //设置消息确认机制
        // rabbitTemplate.setConfirmCallback(rabbitmqConfirmListener);
        //exchange
        rabbitTemplate.convertAndSend(ProcessMqConstant.DIRECT_EXCHANGE,
                //routingKey 路由key 在rabbitmq中交换机Rout Key对应的值
                "future.topic.removeInfo",
                //消息体内容
                message,
                //correlationData消息唯一id
                correlationData);
        log.info("*****RabbitMQ direct 发送消息完成,消息ID为，{} ,参赛信息为 direct competitions: {} *****", messageId, message);
    }


    public static void sendDelayedQueue(String message) {
        log.info("  future delay  ---RabbitMQ开始发送消息***");
        //correlationData消息唯一id
        CorrelationData correlationData = new CorrelationData();
        String messageId = UUID.randomUUID().toString();
        correlationData.setId(messageId);
        //设置消息确认机制
        // rabbitTemplate.setConfirmCallback(rabbitmqConfirmListener);
        //exchange
        rabbitTemplate.convertAndSend(ProcessMqConstant.DELAY_EXCHANGE,
                //routingKey 路由key 在rabbitmq中交换机Rout Key对应的值
                "future.delay.userInfo",
                //消息体内容
                message,
                messages -> {
                    // 设置延迟的毫秒数
                    messages.getMessageProperties().setDelay(10000);
                    messages.getMessageProperties().setHeader("nake","1");
                    messages.getMessageProperties().setExpiration("11000"); //  消息ttl
                    messages.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT); // 消息持久化到磁盘
                    log.info("messages now======: {}", ZonedDateTime.now());
                    return messages;
                },
                //correlationData消息唯一id
                correlationData
              );
        log.info("*****RabbitMQ topic 发送消息完成,消息ID为，{} ,参赛信息为 topiccompetitions: {} *****", messageId, message);
    }



}
