package fun.lance.user.service;

import fun.lance.common.bean.user.dto.RegisterDTO;
import fun.lance.common.bean.user.vo.RegisterVO;

public interface RegisterService {

    RegisterVO register(RegisterDTO registerDTO);

}
