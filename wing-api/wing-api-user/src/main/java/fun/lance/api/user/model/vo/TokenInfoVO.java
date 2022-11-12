package fun.lance.api.user.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TokenInfoVO {
    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("在多少秒后过期")
    private Long expiredIn;

    @ApiModelProperty("用户在自己系统的用户id")
    private Long userId;

    @ApiModelProperty("全局唯一的id")
    private Long uid;

    @ApiModelProperty("租户id (商家id)")
    private Long tenantId;

    @ApiModelProperty("系统类型")
    private Integer sysType;

    @ApiModelProperty("是否是管理员")
    private Integer isAdmin;
}
