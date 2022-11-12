package fun.lance.user.service;

import fun.lance.api.user.model.dto.RegisterDTO;
import fun.lance.api.user.model.vo.RegisterVO;

public interface RegisterService {

    RegisterVO register(RegisterDTO registerDTO);

}
