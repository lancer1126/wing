package fun.lance.common.handler;

import fun.lance.common.resp.RespEnum;
import fun.lance.common.resp.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResultEntity<Object> methodArgumentNotValidExceptionHandler(Exception e) {
        List<FieldError> fieldErrors = null;
        if (e instanceof MethodArgumentNotValidException) {
            fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        }
        if (e instanceof BindException) {
            fieldErrors = ((BindException) e).getBindingResult().getFieldErrors();
        }

        if (fieldErrors == null) {
            return ResultEntity.fail(RespEnum.FAIL);
        }

        List<String> errMessages = fieldErrors.stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());
        return ResultEntity.fail(RespEnum.METHOD_ARGUMENT_NOT_VALID, errMessages);
    }

}
