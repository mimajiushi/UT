package run.ut.app.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception caused by SMS code
 */
public class SmsCodeErrorException extends UtException {
    public SmsCodeErrorException(String message) {
        super(message);
    }

    public SmsCodeErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
