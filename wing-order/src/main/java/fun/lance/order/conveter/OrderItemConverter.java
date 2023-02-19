package fun.lance.order.conveter;

import cn.hutool.core.collection.CollectionUtil;
import fun.lance.order.domain.dto.OrderItemDTO;
import fun.lance.order.domain.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderItemConverter {
    @Mappings({
            @Mapping(target = "totalAmount", expression = "java(dto.getPrice() * dto.getCount())"),
            @Mapping(target = "orderId", source = "orderId"),
    })
    OrderItem dto2Entity(Long orderId, OrderItemDTO dto);

    default List<OrderItem> dto2Entity(Long orderId, List<OrderItemDTO> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            return list.stream().map(dto -> dto2Entity(orderId, dto))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
