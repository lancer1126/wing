package fun.lance.common.exception;

import fun.lance.common.resp.RespEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Object object;

    private RespEnum respEnum;

    public WingException(String message) {
        super(message);
    }

    public WingException(String message, Object object) {
        super(message);
        this.object = object;
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
