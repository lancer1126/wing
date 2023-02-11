package fun.lance.order.domain.vo;

import fun.lance.api.user.dto.MemberAddressDTO;
import fun.lance.order.domain.dto.OrderItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderConfirmVO {

    @ApiModelProperty("订单token")
    private String orderToken;

    @ApiModelProperty("订单明细")
    private List<OrderItemDTO> orderItems;

    @ApiModelProperty("收获地址列表")
    private List<MemberAddressDTO> memberAddresses;

}
