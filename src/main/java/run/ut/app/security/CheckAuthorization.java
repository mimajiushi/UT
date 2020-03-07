package run.ut.app.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * check role
 * @author wenjie
 */
@Retention(RetentionPolicy.RUNTIME) // 为了通过反射取到注解上的东西
public @interface CheckAuthorization {
    String value();
}
