package fun.lance.common.resp;

public enum RespEnum {
    SUCCESS(200, "success"),
    UNAUTHORIZED(401, "Unauthorized"),
    METHOD_ARGUMENT_NOT_VALID(701, "method argument not valid"),
    FAIL(400, "fail");

    private final Integer code;
    private final String msg;

    public Integer value() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    RespEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseEnum{" + "code='" + code + '\'' + ", msg='" + msg + '\'' + "} " + super.toString();
    }
}
