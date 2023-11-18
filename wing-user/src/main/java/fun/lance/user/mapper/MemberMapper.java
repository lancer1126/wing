package fun.lance.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.lance.user.model.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
