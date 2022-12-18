package fun.lance.common.resp;

public enum RespEnum {
    SUCCESS(200, "success"),
    FAIL(400, "fail"),
    UNAUTHORIZED(401, "Unauthorized"),
    METHOD_ARGUMENT_NOT_VALID(701, "method argument not valid"),
    APP_EXCEPTION(777, "application exception"),
    FILE_UPLOAD_ERROR(702, "file upload error"),
    ;

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
