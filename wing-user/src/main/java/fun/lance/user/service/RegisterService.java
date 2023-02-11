package fun.lance.user.service;


import fun.lance.user.domain.dto.RegisterDTO;
import fun.lance.user.domain.vo.RegisterVO;

public interface RegisterService {

    RegisterVO register(RegisterDTO registerDTO);

}
