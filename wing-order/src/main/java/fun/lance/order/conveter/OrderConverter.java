package fun.lance.order.conveter;

import fun.lance.order.domain.dto.OrderSubmitDTO;
import fun.lance.order.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderConverter {
    @Mappings({
            @Mapping(target = "orderSn", source = "orderToken"),
            @Mapping(target = "totalQuantity", expression = "java(orderSubmitDTO.getOrderItems().stream().map(OrderItemDTO::getCount).reduce(0, Integer::sum))"),
            @Mapping(target = "totalAmount", expression = "java(orderSubmitDTO.getOrderItems().stream().map(item -> item.getPrice() * item.getCount()).reduce(0L, Long::sum))"),
    })
    Order submitDTO2Entity(OrderSubmitDTO orderSubmitDTO);
}
