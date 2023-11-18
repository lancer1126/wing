package fun.lance.user.service;


import fun.lance.user.model.dto.RegisterDTO;
import fun.lance.user.model.vo.RegisterVO;

public interface RegisterService {

    RegisterVO register(RegisterDTO registerDTO);

}
