package fun.lance.common.bean.user.bo;

import fun.lance.common.bean.user.vo.AlipayAuthVO;
import lombok.Data;

@Data
public class AlipayAuthRespBO {
    private AlipayAuthVO alipaySystemOauthTokenResponse;
    private AlipayAuthVO alipayUserInfoShareResponse;
    private String sign;
}
