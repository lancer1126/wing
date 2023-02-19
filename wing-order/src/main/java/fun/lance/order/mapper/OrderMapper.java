package fun.lance.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.lance.order.domain.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
