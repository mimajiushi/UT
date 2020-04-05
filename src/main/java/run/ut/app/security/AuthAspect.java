package run.ut.app.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.security.util.JwtOperator;

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
        try {
            // 1. 从header里面获取token
            HttpServletRequest request = getHttpServletRequest();
            String token = request.getHeader("UT-Token");

            // 1. 验证token是否合法；
            this.checkToken();

            // 2. 验证用户角色是否匹配
            Claims claims = jwtOperator.getClaimsFromToken(token);
            int roles = Integer.parseInt(claims.get("roles") + "");

            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            CheckAuthorization annotation = method.getAnnotation(CheckAuthorization.class);

            this.checkRoles(annotation, roles);

            request.setAttribute("uid", claims.get("uid"));
            request.setAttribute("openid", claims.get("openid"));
            request.setAttribute("roles", roles);

        } catch (Throwable throwable) {
            throw new AuthenticationException("用户没有相关权限！");
        }
        return point.proceed();
    }

    @Around("@within(run.ut.app.security.CheckAuthorization)")
    public Object checkAuthorizationType(ProceedingJoinPoint point) throws Throwable {
        try {
            // 1. 从header里面获取token
            HttpServletRequest request = getHttpServletRequest();
            String token = request.getHeader("UT-Token");

            // 1. 验证token是否合法；
            this.checkToken();

            // 2. 验证用户角色是否匹配
            Claims claims = jwtOperator.getClaimsFromToken(token);
            int roles = Integer.parseInt(claims.get("roles") + "");

            CheckAuthorization annotation = point.getTarget().getClass().getAnnotation(CheckAuthorization.class);

            this.checkRoles(annotation, roles);

            request.setAttribute("uid", claims.get("uid"));
            request.setAttribute("openid", claims.get("openid"));
            request.setAttribute("roles", roles);

        } catch (Throwable throwable) {
            throw new AuthenticationException("用户没有相关权限！");
        }
        return point.proceed();
    }

    private void checkRoles(CheckAuthorization annotation, int roles) {
        // get roles from annotation
        String[] values = annotation.roles();
        for (String value : values) {
            int role = UserRolesEnum.getByName(value).getType();
            if ((role & roles) != role) {
                throw new AuthenticationException("用户没有相关权限！");
            }
        }
    }

    private void checkToken() {
        try {
            // 1. 从header里面获取token
            HttpServletRequest request = getHttpServletRequest();

            String token = request.getHeader("UT-Token");

            // 2. 校验token是否合法&是否过期；如果不合法或已过期直接抛异常；如果合法放行
            Boolean isValid = jwtOperator.validateToken(token);
            if (!isValid) {
                throw new SecurityException("Token不合法！");
            }

            // 3. 如果校验成功，那么就将用户的信息设置到request的attribute里面
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
