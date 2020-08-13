package run.ut.app.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception caused by sending SMS code
 */
public class SmsSendException extends UtException {
    public SmsSendException(String message) {
        super(message);
    }

    public SmsSendException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
