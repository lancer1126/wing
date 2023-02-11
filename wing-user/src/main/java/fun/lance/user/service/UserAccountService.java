package fun.lance.user.service;

import fun.lance.user.domain.dto.AuthDTO;
import fun.lance.user.domain.vo.LoginVO;

public interface UserAccountService {
    LoginVO login(AuthDTO authDTO);
}
