package fun.lance.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Object object;

    public WingException(String message) {
        super(message);
    }

    public WingException(String message, Object object) {
        super(message);
        this.object = object;
    }
}
