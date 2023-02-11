package fun.lance.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import fun.lance.api.user.dto.MemberAddressDTO;
import fun.lance.user.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Override
    public List<MemberAddressDTO> listMemberAddress(Long memberId) {
        // 伪数据，仅供测试
        MemberAddressDTO addressDTO = new MemberAddressDTO();
        addressDTO.setId(111L);
        addressDTO.setArea("China");
        addressDTO.setMemberId(memberId);
        addressDTO.setProvince("Shanghai");
        addressDTO.setCity("Shanghai");
        addressDTO.setDetailAddress("火星路11号");
        addressDTO.setConsigneeMobile("1123533");
        return CollUtil.newArrayList(addressDTO);
    }
}
