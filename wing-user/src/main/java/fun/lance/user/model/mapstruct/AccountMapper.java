package fun.lance.user.model.mapstruct;

import fun.lance.api.user.bo.UserInfoTokenBO;
import fun.lance.common.base.BaseStructMapper;
import fun.lance.wing.common.security.bo.AuthAccountVerifyBO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper extends BaseStructMapper<AuthAccountVerifyBO, UserInfoTokenBO> {
}
