package fun.lance.order.service;

import fun.lance.order.domain.vo.OrderConfirmVO;

public interface OrderService {

    /**
     * 订单确认，进入创建订单的页面
     */
    OrderConfirmVO confirmOrder(Long skuId);
}
