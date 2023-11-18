package fun.lance.user.controller;

import fun.lance.api.user.dto.MemberAddressDTO;
import fun.lance.user.model.dto.MemberRegisterDTO;
import fun.lance.common.resp.ResultEntity;
import fun.lance.user.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("添加member")
    @PostMapping()
    public ResultEntity<Object> addMember(@RequestBody MemberRegisterDTO member) {
        return ResultEntity.success(memberService.addMember(member));
    }
}
