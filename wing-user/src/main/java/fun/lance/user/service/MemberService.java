package fun.lance.user.service;

import fun.lance.api.user.dto.MemberAddressDTO;

import java.util.List;

public interface MemberService {
    List<MemberAddressDTO> listMemberAddress(Long memberId);
}
