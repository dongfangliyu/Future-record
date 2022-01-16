package top.goodz.future.domain.listener;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import top.goodz.future.infra.constant.ProcessMqConstant;

import java.util.Map;

@Slf4j
@Service
public class RabbitmqListener {
    /**
     * 自动注入发送消息的模板
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * topic 模式
     * @param competitions
     * @param headers
     * @param channel
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = ProcessMqConstant.TASK_ADD_FUTURE_APPLICATION, durable = "true"),
            exchange = @Exchange(name = ProcessMqConstant.TOPIC_EXCHANGE, type = "topic"),//交换机名称，durable指是否持久化到数据库，type：模式
            key = "future.topic.*" //路由匹配规则
    ))
    @RabbitHandler
    public void competitionMessage(@Payload String competitions,
                                   @Headers Map<String, Object> headers,
                                   Channel channel) throws Exception {
        //消费者操作
        log.info("***** topic CompetitionReceiver:{}，开始消费*****", competitions);
        if (competitions != null) {
            //ACK 手动签收消息，告诉对方消息签收成功,唯一标识ID
            Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliverTag, false);
        }
    }

 /*
    @RabbitListener 自动在RabbitMQ上面添加交换机、队列，以及设置相关属性；
    @Queue和@Exchange的durable属性都是设置队列为持久化。确保退出或者崩溃
    的时候，将会丢失所有队列和消息（交换机没设置是因为该属性默认true）；
    @RabbitHandler 只是标识方法如果有消息过来消费者要消费的时候调用这个方法

    fanout 模式
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = ProcessMqConstant.TASK_FUTURE_APPLICATION, durable = "true"),
            //交换机名称，durable指是否持久化到数据库，type：模式 fanout订阅者模式
            exchange = @Exchange(name = ProcessMqConstant.FANOUT_EXCHANGE, type = "fanout"),
            key = "future.test.*" //路由匹配规则
    ))
    /**
     * @RabbitHandler 只是标识方法如果有消息过来消费者要消费的时候调用这个方法
     */

    @RabbitHandler
    public void competitionDeleteMessage(@Payload String cards,
                                         @Headers Map<String, Object> headers,
                                         Channel channel) throws Exception {
        log.info("***future 中订阅者模式消费者，信息为：{}***", cards);
        //ACK 手动签收消息，告诉对方消息签收成功
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //唯一标识ID
        channel.basicAck(deliverTag, false);
    }

    /**
     * direct 模式
     * @param cards
     * @param headers
     * @param channel
     * @throws Exception
     */

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = ProcessMqConstant.TASK_DELETE_FUTURE_APPLICATION, durable = "true"),
            //交换机名称，durable指是否持久化到数据库，type：模式 fanout订阅者模式
            exchange = @Exchange(name = ProcessMqConstant.DIRECT_EXCHANGE, type = "direct"),
            key = "future.topic.removeInfo" //路由匹配规则
    ))
    /**
     * @RabbitHandler 只是标识方法如果有消息过来消费者要消费的时候调用这个方法
     */

    @RabbitHandler
    public void competitionDeleteMessage2(@Payload String cards,
                                         @Headers Map<String, Object> headers,
                                         Channel channel) throws Exception {
        log.info("***future direct 中订阅者模式消费者，信息为：{}***", cards);
        //ACK 手动签收消息，告诉对方消息签收成功
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        if (false){
            // 拒绝  回到死信队列
            channel.basicNack(deliverTag,false,true);
        }else {
            //唯一标识ID
            channel.basicAck(deliverTag, false);
        }

    }

    /**
     * 延迟队列
     * @param cards
     * @param headers
     * @param channel
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = ProcessMqConstant.TASK_DELAY_FUTURE_APPLICATION, durable = "true"),
            //交换机名称，durable指是否持久化到数据库，type：模式 fanout订阅者模式
            exchange = @Exchange(name = ProcessMqConstant.DELAY_EXCHANGE, type = "x-delayed-message"),
            key = "future.delay.userInfo" //路由匹配规则
    ))
    /**
     * @RabbitHandler 只是标识方法如果有消息过来消费者要消费的时候调用这个方法
     */

    @RabbitHandler
    public void delayQueueListener(@Payload String cards,
                                          @Headers Map<String, Object> headers,
                                          Channel channel) throws Exception {
        log.info("***future delay 中订阅者模式消费者，信息为：{}***", cards);
        //ACK 手动签收消息，告诉对方消息签收成功
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        if (false){
            // 拒绝   第二个参数 false表示是否批量处理   第三个参数为true 回到死信队列
            channel.basicNack(deliverTag,false,true);
        }else {
            //唯一标识ID
            channel.basicAck(deliverTag, false);
        }

    }
}
