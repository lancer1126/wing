package fun.lance.user.mapstruct;

import fun.lance.common.bean.user.dao.AuthAccount;
import fun.lance.common.bean.user.vo.LoginVO;
import fun.lance.common.base.BaseStructMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountStructMapper extends BaseStructMapper<AuthAccount, LoginVO> {
}
