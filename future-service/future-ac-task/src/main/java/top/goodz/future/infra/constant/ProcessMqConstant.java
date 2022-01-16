package top.goodz.future.infra.constant;

/**
 * @author zhang yajun
 */
public class ProcessMqConstant {
    /**
     * 申请服务交换机
     */
    public static final String FANOUT_EXCHANGE = "futureFanoutExchange";

    public static final String TOPIC_EXCHANGE = "futureTopicExchange";

    public static final String DIRECT_EXCHANGE = "futureDirectExchange";

    // 延迟队列交换器
    public static final String DELAY_EXCHANGE = "futureDelayExchange";

    /**
     * queue
     */
    public static final String TASK_FUTURE_APPLICATION = "rabbitmq.queue.futureTaskApplication";

    public static final String TASK_ADD_FUTURE_APPLICATION = "rabbitmq.queue.futureTaskAddApplication";

    public static final String TASK_DELETE_FUTURE_APPLICATION = "rabbitmq.queue.futureTaskDeleteApplication";

    public static final String TASK_DELAY_FUTURE_APPLICATION = "rabbitmq.queue.futureTaskDelayApplication";

}
