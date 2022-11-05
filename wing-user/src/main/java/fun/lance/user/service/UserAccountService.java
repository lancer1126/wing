package fun.lance.user.service;

import fun.lance.api.user.model.dto.AuthDTO;
import fun.lance.api.user.model.vo.LoginVO;

public interface UserAccountService {
    LoginVO login(AuthDTO authDTO);
}
