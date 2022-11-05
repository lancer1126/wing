package fun.lance.user.mapstruct;

import fun.lance.api.user.model.dao.AuthAccount;
import fun.lance.api.user.model.vo.LoginVO;
import fun.lance.common.base.BaseStructMapper;
import fun.lance.common.security.model.bo.AuthAccountVerifyBO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountStructMapper extends BaseStructMapper<AuthAccount, LoginVO> {
}
