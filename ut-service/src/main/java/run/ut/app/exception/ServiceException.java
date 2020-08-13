package run.ut.app.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception caused by service.
 *
 * @author wenjie
 */
public class ServiceException extends UtException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
