package fun.lance.common.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义返回集
 */
@Data
public class ResultEntity<T> implements Serializable {

    private Boolean success;
    private Integer code;
    private String msg;
    private T data;

    public ResultEntity() {}

    public ResultEntity(Boolean success, Integer code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultEntity<T> success() {
        return new ResultEntity<>(true, RespEnum.SUCCESS.value(), "success", null);
    }

    public static <T> ResultEntity<T> success(T data) {
        return new ResultEntity<>(true, RespEnum.SUCCESS.value(), "success", data);
    }

    public static <T> ResultEntity<T> success(String msg, T data) {
        return new ResultEntity<>(true, RespEnum.SUCCESS.value(), msg, data);
    }

    public static <T> ResultEntity<T> fail(String msg, T data) {
        return new ResultEntity<>(false, RespEnum.FAIL.value(), msg, data);
    }

    public static <T> ResultEntity<T> fail(RespEnum respEnum) {
        return new ResultEntity<>(false, respEnum.value(), respEnum.getMsg(), null);
    }

    public static <T> ResultEntity<T> fail(RespEnum respEnum, T data) {
        return new ResultEntity<>(false, respEnum.value(), respEnum.getMsg(), data);
    }

    public static <T> ResultEntity<T> fail(RespEnum respEnum, String msg, T data) {
        return new ResultEntity<>(false, respEnum.value(), msg, data);
    }

    public static <T> ResultEntity<T> result(Boolean success, RespEnum respEnum, String msg, T data) {
        return new ResultEntity<>(success, respEnum.value(), msg, data);
    }
}
