package fun.lance.user.controller;

import fun.lance.common.resp.ResultEntity;
import fun.lance.user.model.dto.AlipayAuthDTO;
import fun.lance.user.model.dto.AuthDTO;
import fun.lance.user.model.vo.AlipayAuthVO;
import fun.lance.user.model.vo.LoginVO;
import fun.lance.user.service.AlipayService;
import fun.lance.user.service.UserAccountService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "登录")
@RestController
@RequestMapping("/ua")
@RequiredArgsConstructor
public class LoginController {

    private final UserAccountService userAccountService;
    private final AlipayService alipayService;

    @PostMapping ("/login")
    public ResultEntity<LoginVO> login(@Valid @RequestBody AuthDTO authDTO) {
        return ResultEntity.success(userAccountService.login(authDTO));
    }

    @PostMapping("/alipay/login")
    public ResultEntity<AlipayAuthVO> loginByAlipay() {
        return ResultEntity.success(alipayService.getRedirectUrl());
    }

    @PostMapping("/alipay/redirect")
    public ResultEntity<AlipayAuthVO> alipayCallback(@RequestBody AlipayAuthDTO alipayDTO) {
        return ResultEntity.success(alipayService.alipayCallback(alipayDTO));
    }
}
