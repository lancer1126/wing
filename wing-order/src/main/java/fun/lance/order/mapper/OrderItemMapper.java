package fun.lance.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.lance.order.domain.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
