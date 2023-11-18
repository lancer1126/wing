package fun.lance.user.service;

import fun.lance.user.model.dto.AuthDTO;
import fun.lance.user.model.vo.LoginVO;

public interface UserAccountService {
    LoginVO login(AuthDTO authDTO);
}
