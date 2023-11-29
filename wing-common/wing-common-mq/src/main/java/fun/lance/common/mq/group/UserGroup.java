package fun.lance.common.mq.group;

public class UserGroup {
    public static final String USER_EXCHANGE = "user.exchange";
    public static final String USER_DLX_EXCHANGE = "user.dlx.exchange";
    public static final String USER_FANOUT_EXCHANGE = "user_fanout_exchange";

    public static final String USER_QUEUE = "user.queue";
    public static final String USER_DLX_QUEUE = "user.dlx.queue";
    public static final String USER_BIZ_QUEUE = "user_biz_queue";
    public static final String USER_ORDER_QUEUE = "user.order.queue";

    public static final String USER_ROUTING_KEY = "user.routing.key";
    public static final String USER_DLX_ROUTING_KEY = "user.dlx.routing.key";
    public static final String USER_FANOUT_ROUTING_KEY = "#";
}
