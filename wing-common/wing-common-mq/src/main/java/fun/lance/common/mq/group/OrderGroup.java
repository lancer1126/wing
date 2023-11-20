package fun.lance.common.mq.group;

public class OrderGroup {

    /**
     * 订单防重提交锁KEY前缀
     */
    public static final String ORDER_RESUBMIT_LOCK_PREFIX = "ORDER:RESUBMIT_LOCK:";

    public static class MQ {
        // 普通订单相关
        public static final String ORDER_DELAY_QUEUE = "order.delay.queue";
        public static final String ORDER_EXCHANGE = "order.exchange";
        public static final String ORDER_DELAY_ROUTING_KEY = "order.delay.routing.key";

        // 关闭订单相关
        public static final String ORDER_DLX_QUEUE = "order.dlx.queue";
        public static final String ORDER_DLX_EXCHANGE = "order.dlx.exchange";
        public static final String ORDER_DLX_ROUTING_KEY = "order.dlx.routing.key";
    }

}
