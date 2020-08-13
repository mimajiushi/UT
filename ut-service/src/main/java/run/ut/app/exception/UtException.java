package run.ut.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
}
