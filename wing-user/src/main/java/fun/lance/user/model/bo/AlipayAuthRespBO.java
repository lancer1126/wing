package fun.lance.user.model.bo;

import fun.lance.user.model.vo.AlipayAuthVO;
import lombok.Data;

@Data
public class AlipayAuthRespBO {
    private AlipayAuthVO alipaySystemOauthTokenResponse;
    private AlipayAuthVO alipayUserInfoShareResponse;
    private String sign;
}
