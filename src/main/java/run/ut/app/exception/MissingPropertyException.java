package run.ut.app.exception;

/**
 * Missing property value exception.
 *
 * @author wenjie
 */
public class MissingPropertyException extends BadRequestException {

    public MissingPropertyException(String message) {
        super(message);
    }

    public MissingPropertyException(String message, Throwable cause) {
        super(message, cause);
    }
}
