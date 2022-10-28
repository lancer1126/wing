package fun.lance.user.controller;

import fun.lance.api.user.bo.UserInfoTokenBO;
import fun.lance.api.user.vo.TokenInfoVO;
import fun.lance.common.resp.ResultEntity;
import fun.lance.user.dto.AuthDTO;
import fun.lance.user.service.UserAccountService;
import fun.lance.user.utils.MessageUtil;
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
    public ResultEntity<TokenInfoVO> login(@Valid @RequestBody AuthDTO authDTO) {
        UserInfoTokenBO userInfoToken = userAccountService
                .getUserInfoToken(authDTO.getPrincipal(), authDTO.getCredentials(), authDTO.getSysType());
        return ResultEntity.success(null);
    }
}
