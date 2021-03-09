package run.ut.app.exception;

import org.springframework.http.HttpStatus;

/**
 * 权限注解使用错误异常
 *
 * @author chenwenjie.star
 * @date 2021/3/9 8:04 下午
 */
public class AuthConfigException extends UtException {

    public AuthConfigException(String message) {
        super(message);
    }

    public AuthConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}