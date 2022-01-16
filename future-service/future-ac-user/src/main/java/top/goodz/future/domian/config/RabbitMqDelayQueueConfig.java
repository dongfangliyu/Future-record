package top.goodz.future.domian.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.goodz.future.domian.constant.ProcessMqConstant;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * 掩饰队列实现配置
 */
@Configuration
public class RabbitMqDelayQueueConfig {


    @Bean
    public Queue queue() {
        return new Queue(ProcessMqConstant.TASK_DELAY_FUTURE_APPLICATION);
    }

    // 配置默认的交换机
    @Bean
    CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        //参数二为类型：必须是x-delayed-message
        return new CustomExchange(ProcessMqConstant.DELAY_EXCHANGE, "x-delayed-message", true, false, args);
    }

    // 绑定队列到交换器
    @Bean
    Binding binding(Queue queue, CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ProcessMqConstant.TASK_DELAY_FUTURE_APPLICATION).noargs();
    }

}
