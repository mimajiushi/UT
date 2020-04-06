package run.ut.app.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * check role
 * @author wenjie
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) // 为了通过反射取到注解上的东西
public @interface CheckAuthorization {
    String[] roles() default {};
}
