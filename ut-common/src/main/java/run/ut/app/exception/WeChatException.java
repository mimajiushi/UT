package run.ut.app.exception;

import org.springframework.http.HttpStatus;

/**
 * about wechat's exception.
 *
 * @author wenjie
 */
public class WeChatException extends UtException {

    public WeChatException(String message) {
        super(message);
    }

    public WeChatException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
