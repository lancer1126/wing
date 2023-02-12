package fun.lance.user.domain.vo;

import fun.lance.user.domain.dto.AlipayAuthDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlipayAuthVO extends AlipayAuthDTO {
    private String redirectUrl;
    private String accessToken;
    private String alipayUserId;
    private String authStart;
    private String expiresIn;
    private String reExpiresIn;
    private String refreshToken;
    private String userId;
    private String sign;
    private String avatar;
    private String nickName;
}