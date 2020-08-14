package run.ut.app.exception;

/**
 * WebSocket exception.
 *
 * @author wenjie
 */
public class WebSocketException extends ServiceException {
    public WebSocketException(String message) {
        super(message);
    }

    public WebSocketException(String message, Throwable cause) {
        super(message, cause);
    }
}
