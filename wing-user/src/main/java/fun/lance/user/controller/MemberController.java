package fun.lance.user.controller;

import fun.lance.api.user.dto.MemberAddressDTO;
import fun.lance.common.resp.ResultEntity;
import fun.lance.user.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiOperation("获取会员地址列表")
    @GetMapping("/address")
    public ResultEntity<List<MemberAddressDTO>> listMemberAddress(@RequestParam Long memberId) {
        return ResultEntity.success(memberService.listMemberAddress(memberId));
    }
}
