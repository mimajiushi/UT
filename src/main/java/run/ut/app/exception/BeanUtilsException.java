package run.ut.app.exception;

import org.springframework.http.HttpStatus;

/**
 * BeanUtils exception.
 *
 * @author wenjie
 */
public class BeanUtilsException extends UtException {

    public BeanUtilsException(String message) {
        super(message);
    }

    public BeanUtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
