package run.ut.app.cache.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import run.ut.app.exception.FrequentAccessException;
import run.ut.app.model.enums.RateLimitEnum;
import run.ut.app.service.RedisService;
import run.ut.app.utils.ServletUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * RequestRateLimit's aspect (only IP current limiting is implemented)
 * @author wenjie
 */

@Component
@Aspect
@Slf4j
public class HttpRequestRateLimitAspect {

    @DubboReference
    private RedisService redisService;

    @Around("@annotation(run.ut.app.cache.lock.HttpRequestRateLimit)")
    public Object requestRateLimit(ProceedingJoinPoint point) throws Throwable {
        // Gets request URI
        String requestURI = ServletUtils.getRequestURI();
        // Gets user-agent from header
        String userAgent = ServletUtils.getHeaderIgnoreCase("user-agent");
        // Gets IP
        String requestIp = ServletUtils.getRequestIp();

        // Gets method
        final Method method = ((MethodSignature) point.getSignature()).getMethod();
        // Gets annotation
        HttpRequestRateLimit requestRateLimit = method.getAnnotation(HttpRequestRateLimit.class);
        // Gets annotation params
        RateLimitEnum limitEnum = requestRateLimit.limit();
        TimeUnit timeUnit = requestRateLimit.timeUnit();
        int[] limitParams = getLimitParams(limitEnum);

        // Generates key
        String key = String.format("ut_api_request_limit_rate_%s_%s_%s", requestIp, method.getName(), requestURI);
        // Checks
        boolean over = redisService.overRequestRateLimit(key, limitParams[0], limitParams[1], timeUnit, userAgent);
        if (over) {
            throw new FrequentAccessException("请求过于频繁，请稍后重试。");
        }

        return point.proceed();
    }

    /***
     * In the returned array,
     *
     * @return ↓
     * elements[0] is the time limit,
     * elements[1] is the number of times that can be requested within the time limit.
     */
    private static int[] getLimitParams(RateLimitEnum rateLimitEnum) {
        String limit = rateLimitEnum.limit();
        int[] result = new int[2];
        String[] limits = limit.split("/");
        result[0] = Integer.parseInt(limits[0]);
        result[1] = Integer.parseInt(limits[1]);
        return result;
    }

}
