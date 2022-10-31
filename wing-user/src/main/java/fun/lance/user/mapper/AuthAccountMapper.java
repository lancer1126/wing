package fun.lance.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.lance.user.model.AuthAccount;
import fun.lance.wing.common.security.bo.AuthAccountVerifyBO;
import org.apache.ibatis.annotations.Param;

public interface AuthAccountMapper extends BaseMapper<AuthAccount> {

    AuthAccountVerifyBO getAuthAccountInfoByUsername(@Param("userNameType") Integer userNameType,
                                                     @Param("userName") String userName,
                                                     @Param("sysType") Integer sysType);

}
