package run.ut.app.security;

import run.ut.app.model.enums.UserRolesEnum;

import java.lang.annotation.*;

/**
 * check role
 * @author wenjie
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckAuthorization {

    UserRolesEnum[] roles() default {};

}
