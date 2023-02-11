package fun.lance.user.domain.bo;

import fun.lance.user.domain.vo.AlipayAuthVO;
import lombok.Data;

@Data
public class AlipayAuthRespBO {
    private AlipayAuthVO alipaySystemOauthTokenResponse;
    private AlipayAuthVO alipayUserInfoShareResponse;
    private String sign;
}
