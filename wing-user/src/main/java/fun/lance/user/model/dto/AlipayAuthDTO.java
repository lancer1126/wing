package fun.lance.user.model.dto;

import lombok.Data;

@Data
public class AlipayAuthDTO {
    private String authCode;
    private String authToken;
}
