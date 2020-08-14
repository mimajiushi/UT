package run.ut.app.cache.lock;


import run.ut.app.model.enums.RateLimitEnum;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * Annotation for current limiting
 *
 * @author wenjie
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpRequestRateLimit {
    RateLimitEnum limit();

    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
