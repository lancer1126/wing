package fun.lance.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.lance.order.domain.entity.OrderItem;
import fun.lance.order.mapper.OrderItemMapper;
import fun.lance.order.service.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
}
