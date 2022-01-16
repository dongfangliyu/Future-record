package top.goodz.future.domian.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class RabbitmqConfirmListener  implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);            //指定 ConfirmCallback
    }

        /**
         * 回调函数:confirm确认rabbitmq的ack接收情况
         *
         * @param correlationData 消息信息
         * @param ack             消息是否返回成功
         * @author Czw
         * @date 2019/10/12 0015 下午 8:51
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String s) {
            log.debug("RabbitMQ回调函数，CorrelationData：{},是否成功：{}", correlationData, ack);
            //获取消息ID
            String messageId = correlationData == null ? "" : correlationData.getId();
            String messageReturn = correlationData == null ? "" : correlationData.toString();
            if (ack) {

                log.info("RabbitMQ回调函数返回成功,messageId为： {} ，内容为： {} ", messageId, messageReturn);
            } else {
                log.error("RabbitMQ回调函数返回失败");
            }
        }
}
