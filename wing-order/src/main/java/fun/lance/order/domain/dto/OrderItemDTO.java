package fun.lance.order.domain.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    /**
     * 商品库存单元ID
     */
    private Long skuId;

    /**
     * SKU编码
     */
    private String skuSn;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * 商品图片地址
     */
    private String picUrl;

    /**
     * 商品价格
     */
    private Long price;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 订单商品数量
     */
    private Integer count;
}
