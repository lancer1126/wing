package fun.lance.order.controller;

import fun.lance.common.resp.ResultEntity;
import fun.lance.common.utils.MessageUtil;
import fun.lance.order.domain.dto.OrderSubmitDTO;
import fun.lance.order.domain.vo.OrderConfirmVO;
import fun.lance.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("订单提交")
    @PostMapping("/submit")
    public ResultEntity<Object> submitOrder(@RequestBody @Validated OrderSubmitDTO orderSubmitDTO) {
        return ResultEntity.success(orderService.submitOrder(orderSubmitDTO));
    }

    @ApiOperation("订单传输")
    @PostMapping("/transfer")
    public ResultEntity<Object> transferOrder(@RequestBody @Validated OrderSubmitDTO orderSubmitDTO) {
        orderService.transferOrder(orderSubmitDTO);
        return ResultEntity.success();
    }

}
