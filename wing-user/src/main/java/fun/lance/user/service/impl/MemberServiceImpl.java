package fun.lance.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.lance.api.user.dto.MemberAddressDTO;
import fun.lance.user.model.dto.MemberRegisterDTO;
import fun.lance.user.common.constants.MessageConst;
import fun.lance.user.convert.MemberConvert;
import fun.lance.user.mapper.MemberMapper;
import fun.lance.user.model.entity.Member;
import fun.lance.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    private final MemberConvert memberConvert;
    private final RabbitTemplate rabbitTemplate;

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

    @Override
    public Long addMember(MemberRegisterDTO memberDTO) {
        Member member = memberConvert.dto2Entity(memberDTO);
        boolean save = save(member);
        Assert.isTrue(save, "新增会员失败");
        rabbitTemplate.convertAndSend(MessageConst.USER_EXCHANGE, MessageConst.USER_ROUTING_KEY, member);
        return member.getId();
    }
}
