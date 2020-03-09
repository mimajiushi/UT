package run.ut.app.exception;

/**
 * File operation exception.
 *
 * @author wenjie
 */
public class FileOperationException extends ServiceException {
    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
