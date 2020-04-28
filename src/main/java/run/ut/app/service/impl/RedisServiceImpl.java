package run.ut.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import run.ut.app.service.RedisService;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wenjie
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void set(String key, String value) {
        Assert.hasText(key, "redis key must not be blank");
        Assert.hasText(value, "redis value must not be blank");

        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public long setList(String key, List list) {
        Assert.hasText(key, "redis key must not be blank");
        Assert.notEmpty(list, "redis list must not be empty");

        Long count = stringRedisTemplate.opsForList().rightPushAll(key, list);
        if (null == count) {
            count = 0L;
        }
        return count;
    }

    @Override
    public String get(String key) {
        Assert.hasText(key, "redis key must not be blank");
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean setKeyValTTL(String key, String value, long ttl) {
        Assert.hasText(key, "redis key must not be blank");
        Assert.hasText(value, "redis incrementvalue must not be blank");

        stringRedisTemplate.boundValueOps(key).set(value,ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (null == expire) {
            expire = 0L;
        }
        return expire > 0;
    }

    @Override
    public boolean expire(String key, long expire) {
        Assert.hasText(key, "redis key must not be blank");
        Boolean res = stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        if (null == res) {
            res = false;
        }
        return res;
    }

    @Override
    public boolean expire(String key, long expire, TimeUnit unit) {
        Assert.hasText(key, "redis key must not be blank");
        Boolean res = stringRedisTemplate.expire(key, expire, unit);
        if (null == res) {
            res = false;
        }
        return res;
    }

    @Override
    public boolean remove(String key) {
        Assert.hasText(key, "redis key must not be blank");
        Boolean deleted = stringRedisTemplate.delete(key);
        if (null == deleted) {
            deleted = false;
        }
        return deleted;
    }

    @Override
    public Boolean zadd(String key, String menber, double score) {
        return stringRedisTemplate.opsForZSet().add(key, menber, score);
    }

    @Override
    public Double zIncrement(String key, String menber, double delta) {
        return stringRedisTemplate.opsForZSet().incrementScore(key, menber, delta);
    }

    @Override
    public Long increment(String key, int variable) {
        Assert.hasText(key, "key must not be blank");
        return stringRedisTemplate.opsForValue().increment(key, variable);
    }

    @Override
    public Double zscore(String key, String member) {
        Assert.hasText(key, "key must not be blank");
        Assert.hasText(member, "member must not be blank");
        return stringRedisTemplate.opsForZSet().score(key, member);
    }

    @Override
    public Long zrem(String key, Object... members) {
        Assert.hasText(key, "key must not be blank");
        return stringRedisTemplate.opsForZSet().remove(key, members);
    }

    @Override
    public boolean overRequestRateLimit(String key, int max, int expireTime, TimeUnit timeUnit, String userAgent) {
        Assert.hasText(key, "redis key must not be blank");

        long count = increment(key, 1);
        long time = stringRedisTemplate.getExpire(key);
        /*
         * count = 1: 表示在本次请求前，key不存在或者key已过期。
         * time = -1: 表示未设置过期时间
         */
        if (count == 1 || time == -1) {
            expire(key, expireTime, timeUnit);
        }

        log.debug("Express api request limit rate:too many requests: key={}, redis count={}, max count={}, " +
            "expire time= {} s, user-agent={} ", key, count, max, expireTime, userAgent);

        return count > max;
    }
}
