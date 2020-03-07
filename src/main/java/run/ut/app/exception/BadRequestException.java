package run.ut.app.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception caused by bad request.
 *
 * @author wenjie
 */
public class BadRequestException extends UtException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
