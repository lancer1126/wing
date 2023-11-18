package fun.lance.user.convert;

import fun.lance.user.model.dto.MemberRegisterDTO;
import fun.lance.user.model.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberConvert {

    Member dto2Entity(MemberRegisterDTO memberRegisterDTO);
}
