package run.ut.app.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception caused by accessing forbidden resources.
 *
 * @author wenjie
 */
public class ForbiddenException extends UtException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
