package fun.lance.wing.common.security.bo;

import fun.lance.api.user.bo.UserInfoTokenBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthAccountVerifyBO extends UserInfoTokenBO {
    private String password;
    private Integer status;
}
