package fun.lance.order.service;

import fun.lance.order.domain.dto.OrderSubmitDTO;
import fun.lance.order.domain.vo.OrderConfirmVO;
import fun.lance.order.domain.vo.OrderSubmitResultVO;

public interface OrderService {

    /**
     * 订单确认，进入创建订单的页面
     */
    OrderConfirmVO confirmOrder(Long skuId);

    OrderSubmitResultVO submitOrder(OrderSubmitDTO orderSubmitDTO);

    /**
     * 系统关闭订单
     */
    void closeOrder(String orderSn);

    void transferOrder(OrderSubmitDTO orderSubmitDTO);
}
