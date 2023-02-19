package fun.lance.order.common.enums;

import lombok.Getter;

public enum OrderStatusEnum {
    /**
     * 待付款
     */
    UNPAID(0, "待付款"),
    /**
     * 已付款(待发货)
     */
    PAID(1, "已付款"),
    /**
     * 已发货
     */
    SHIPPED(2, "已发货"),
    /**
     * 已完成
     */
    COMPLETE(3, "已完成"),
    /**
     * 已取消
     */
    CANCELED(4, "已取消"),
    /**
     * 售后中
     */
    SERVICING(5, "售后中")
    ;

    OrderStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @Getter
    private final Integer value;
    @Getter
    private final String label;

}
