package fun.lance.user.domain.dto;

import lombok.Data;

@Data
public class AlipayAuthDTO {
    private String authCode;
    private String authToken;
}
