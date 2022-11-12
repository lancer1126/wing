package fun.lance.user.controller;

import fun.lance.api.user.model.dto.RegisterDTO;
import fun.lance.api.user.model.vo.RegisterVO;
import fun.lance.common.resp.ResultEntity;
import fun.lance.user.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "用户注册接口")
@RestController
@RequestMapping("/ua/user/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @ApiOperation("注册")
    @PostMapping
    public ResultEntity<RegisterVO> register(@Valid @RequestBody RegisterDTO registerDTO) {
        return ResultEntity.success(registerService.register(registerDTO));
    }

}
