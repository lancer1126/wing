package fun.lance.order.controller;

import fun.lance.common.resp.ResultEntity;
import fun.lance.common.utils.MessageUtil;
import fun.lance.order.domain.vo.OrderConfirmVO;
import fun.lance.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ua/app/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ApiOperation("订单确认")
    @PostMapping
    public ResultEntity<OrderConfirmVO> confirmOrder(@RequestParam(required = false) Long skuId) {
        return ResultEntity.success(orderService.confirmOrder(skuId));
    }

}
