package fun.lance.user.converter;

import fun.lance.api.user.bo.UserInfoTokenBO;
import fun.lance.wing.common.security.bo.AuthAccountVerifyBO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountConverter {
    UserInfoTokenBO convert(AuthAccountVerifyBO accountVerifyBO);
}
