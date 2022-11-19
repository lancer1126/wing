package fun.lance.user.service;

import fun.lance.common.bean.user.dto.AuthDTO;
import fun.lance.common.bean.user.vo.LoginVO;

public interface UserAccountService {
    LoginVO login(AuthDTO authDTO);
}
