package run.ut.app.exception;

import org.springframework.http.HttpStatus;

/**
 * Authentication exception.
 *
 * @author wenjie
 */
public class AuthenticationException extends UtException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
