package fun.lance.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.lance.api.user.dto.MemberAddressDTO;
import fun.lance.common.mq.group.UserGroup;
import fun.lance.user.convert.MemberConvert;
import fun.lance.user.mapper.MemberMapper;
import fun.lance.user.model.dto.MemberRegisterDTO;
import fun.lance.user.model.entity.Member;
import fun.lance.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
        // 测试数据
        memberDTO.setCity("shanghai");
        memberDTO.setProvince("shanghai");
        memberDTO.setBirthday(LocalDate.now());
        memberDTO.setCountry("china");
        memberDTO.setAvatarUrl("xxx.net");
        memberDTO.setLanguage("zh");
        memberDTO.setMobile("12431");
        memberDTO.setNickName("nnn" + UUID.randomUUID().toString().substring(0, 4));
        memberDTO.setSessionKey("kkk" + UUID.randomUUID().toString().substring(0, 4));
        memberDTO.setOpenid("xxx" + UUID.randomUUID().toString().substring(0, 4));

        Member member = memberConvert.dto2Entity(memberDTO);
        boolean save = save(member);
        Assert.isTrue(save, "新增会员失败");

        rabbitTemplate.convertAndSend(
                UserGroup.USER_FANOUT_EXCHANGE, UserGroup.USER_FANOUT_ROUTING_KEY, JSON.toJSONString(member), new CorrelationData(member.getOpenid())
        );
        return member.getId();
    }
}
