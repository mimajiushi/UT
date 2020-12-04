package run.ut.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.ut.app.utils.JsonUtils;

import java.util.Map;

/**
 * Base exception of the project.
 *
 * @author wenjie
 */
public abstract class UtException extends RuntimeException {

    /**
     * Error errorData.
     */
    private Object errorData;

    public UtException(String message) {
        super(message);
    }

    public UtException(String message, Throwable cause) {
        super(message, cause);
    }

    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    /**
     * Sets error errorData.
     *
     * @param errorData error data
     * @return current exception.
     */
    @NonNull
    public UtException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }

    public static void main(String[] args) {
        String json = "{\"links\":[{\"start\":24,\"length\":33,\"link\":\"sslocal://webview?disable_hash=1\\u0026disable_tt_referer=1\\u0026disable_tt_ua=1\\u0026show_bottom_toolbar=1\\u0026url=http%3A%2F%2Fboe.t.zijieimg.com%2FZ0UDua%2F\",\"type\":3,\"text\":\"网页链接\",\"id\":0,\"id_type\":0}]}";
        Map map = JsonUtils.jsonToObject(json, Map.class);
        System.out.println(map);
    }
}
