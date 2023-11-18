package fun.lance.user.service;

import fun.lance.api.user.dto.MemberAddressDTO;
import fun.lance.user.model.dto.MemberRegisterDTO;

import java.util.List;

public interface MemberService {
    List<MemberAddressDTO> listMemberAddress(Long memberId);

    Long addMember(MemberRegisterDTO member);
}
