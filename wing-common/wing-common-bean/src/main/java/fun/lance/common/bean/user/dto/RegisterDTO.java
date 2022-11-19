package fun.lance.common.bean.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("用户注册信息")
public class RegisterDTO {

    @NotBlank
    @ApiModelProperty(value = "用户名")
    private String userName;

    @NotBlank
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String img;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "当账户未绑定时，临时的uid")
    private String tempUid;

    @ApiModelProperty(value = "用户id")
    private Long userId;
}
