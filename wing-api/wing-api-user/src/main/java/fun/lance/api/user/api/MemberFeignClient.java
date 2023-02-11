package fun.lance.api.user.api;

import fun.lance.api.user.dto.MemberAddressDTO;
import fun.lance.common.resp.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "wing-user", contextId = "member")
public interface MemberFeignClient {

    @GetMapping("/app/member/address")
    ResultEntity<List<MemberAddressDTO>> listMemberAddresses(@RequestParam("memberId") Long memberId);

}
