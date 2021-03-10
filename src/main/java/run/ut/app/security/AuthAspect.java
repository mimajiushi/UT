package run.ut.app.security;

import cn.hutool.core.util.ArrayUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import run.ut.app.exception.AuthConfigException;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.security.util.JwtOperator;
import run.ut.app.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 处理认证注解
 *
 * 使用位运算实现角色校验
 *
 * @author wenjie
 */

@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthAspect {
    private final JwtOperator jwtOperator;

    @Around("@annotation(run.ut.app.security.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
        checkToken();
        return point.proceed();
    }

    @Around("@annotation(run.ut.app.security.CheckAuthorization)")
    public Object checkAuthorizationMethod(ProceedingJoinPoint point) throws Throwable {
        this.checkAuthorization(point);
        return point.proceed();
    }

    @Around("@within(run.ut.app.security.CheckAuthorization)")
    public Object checkAuthorizationType(ProceedingJoinPoint point) throws Throwable {
        this.checkAuthorization(point);
        return point.proceed();
    }

    private void checkAuthorization(ProceedingJoinPoint point) {
        try {
            // 1. get token from header
            HttpServletRequest request = getHttpServletRequest();
            String token = ServletUtils.getHeaderIgnoreCase("UT-Token");

            // 1. check token
            this.checkToken();

            // 2. check roles
            Claims claims = jwtOperator.getClaimsFromToken(token);
            int roles = Integer.parseInt(claims.get("roles") + "");

            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            CheckAuthorization annotation = method.getAnnotation(CheckAuthorization.class);
            if (ObjectUtils.isEmpty(annotation)) {
                annotation = point.getTarget().getClass().getAnnotation(CheckAuthorization.class);
            }

            this.checkRoles(annotation, roles);

            request.setAttribute("uid", claims.get("uid"));
            request.setAttribute("openid", claims.get("openid"));
            request.setAttribute("roles", roles);
        } catch (Throwable throwable) {
            throw new AuthenticationException("用户没有相关权限！");
        }
    }

    private void checkRoles(CheckAuthorization annotation, int roles) {
        // get roles from annotation
        UserRolesEnum[] rolesEnums = annotation.roles();
        UserRolesEnum[] excludeRoles = annotation.excludeRoles();
        if (ArrayUtil.isAllNotEmpty(rolesEnums, excludeRoles)) {
            throw new AuthConfigException("CheckAuthorization注解中roles()和excludeRoles()不能并存");
        }
        for (UserRolesEnum roleEnum : rolesEnums) {
            int role = roleEnum.getType();
            if ((role & roles) != role) {
                throw new AuthenticationException("用户没有相关权限！");
            }
        }
        // exclude roles
        for (UserRolesEnum excludeRole : excludeRoles) {
            int role = excludeRole.getType();
            if (
                    role == UserRolesEnum.ROLE_TOURIST.getType() ||
                    roles == UserRolesEnum.ROLE_TOURIST.getType()
            ) {
                if (role == roles) {
                    throw new AuthenticationException("用户没有相关权限！");
                } else {
                    return;
                }
            }
            if ((role & roles) == role) {
                throw new AuthenticationException("用户没有相关权限！");
            }
        }
    }

    private void checkToken() {
        try {
            // 1. get token from header
            HttpServletRequest request = getHttpServletRequest();

            String token = ServletUtils.getHeaderIgnoreCase("UT-Token");

            // 2. check token
            Boolean isValid = jwtOperator.validateToken(token);
            if (!isValid) {
                throw new SecurityException("Token不合法！");
            }

            // 3. If the verification is successful, set the user's uid,openid,roles to the attribute of the request
            Claims claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("uid", claims.get("uid"));
            request.setAttribute("openid", claims.get("openid"));
            request.setAttribute("roles", claims.get("roles"));
        } catch (Throwable throwable) {
            throw new AuthenticationException("Token不合法/登录状态过期，请重新登录");
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        return attributes.getRequest();
    }
}
