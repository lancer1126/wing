package fun.lance.common.bean.user.dto;

import lombok.Data;

@Data
public class AlipayAuthDTO {
    private String authCode;
    private String authToken;
}
