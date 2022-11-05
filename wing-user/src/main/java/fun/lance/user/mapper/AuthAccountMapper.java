package fun.lance.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.lance.api.user.model.dao.AuthAccount;
import fun.lance.common.security.model.bo.AuthAccountVerifyBO;
import org.apache.ibatis.annotations.Param;

public interface AuthAccountMapper extends BaseMapper<AuthAccount> {

    AuthAccountVerifyBO getAuthAccountInfoByUsername(@Param("userNameType") Integer userNameType,
                                                     @Param("userName") String userName,
                                                     @Param("sysType") Integer sysType);

}
