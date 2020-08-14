package run.ut.app.exception;

/**
 * Email exception.
 *
 * @author wenjie
 * @date 2020-4-6
 */
public class EmailException extends ServiceException {

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
