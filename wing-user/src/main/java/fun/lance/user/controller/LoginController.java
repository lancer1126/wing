package fun.lance.user.controller;

import fun.lance.api.user.model.dto.AuthDTO;
import fun.lance.api.user.model.vo.LoginVO;
import fun.lance.common.resp.ResultEntity;
import fun.lance.user.service.UserAccountService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "登录")
@RestController
@RequestMapping("/ua")
@RequiredArgsConstructor
public class LoginController {

    private final UserAccountService userAccountService;

    @PostMapping ("/login")
    public ResultEntity<LoginVO> login(@Valid @RequestBody AuthDTO authDTO) {
        return ResultEntity.success(userAccountService.login(authDTO));
    }
}
