package fun.lance.user.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginVO extends TokenInfoVO{

    @ApiModelProperty(value = "若是第三方授权登录，授权地址")
    private String redirectUrl;
}
