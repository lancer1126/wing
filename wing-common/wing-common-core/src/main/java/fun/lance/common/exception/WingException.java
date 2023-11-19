package fun.lance.common.exception;

import fun.lance.common.resp.RespEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class WingException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer code;

    private Object object;

    private RespEnum respEnum;

    public WingException(String message) {
        super(message);
    }

    public WingException(String message, Object object) {
        super(message);
        this.object = object;
    }

    public WingException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public WingException(RespEnum respEnum) {
        super(respEnum.getMsg());
        this.respEnum = respEnum;
    }

    public WingException(RespEnum respEnum, Object object) {
        super(respEnum.getMsg());
        this.respEnum = respEnum;
        this.object = object;
    }
}
