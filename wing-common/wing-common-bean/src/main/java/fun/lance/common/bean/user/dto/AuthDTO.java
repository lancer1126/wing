package fun.lance.common.bean.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AuthDTO {

    @NotBlank(message = "principal不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String principal;

    @NotBlank(message = "credentials不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String credentials;

    @NotNull(message = "sysType不能为空")
    @ApiModelProperty(value = "系统类型，0：用户系统，1：商家系统", required = true)
    private Integer sysType;
}
