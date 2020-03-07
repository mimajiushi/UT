package run.ut.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import run.ut.app.service.RedisService;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wenjie
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public long setList(String key, List list){
        Long count = redisTemplate.opsForList().rightPushAll(key, list);
        if (null == count){
            count = 0L;
        }
        return count;
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean setKeyValTTL(String key, String value, long ttl) {
        stringRedisTemplate.boundValueOps(key).set(value,ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (null == expire){
            expire = 0L;
        }
        return expire > 0;
    }

    @Override
    public boolean expire(String key, long expire) {
        Boolean res = stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        if (null == res){
            res = false;
        }
        return res;
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Boolean zadd(String key, String menber, double score) {
        return stringRedisTemplate.opsForZSet().add(key, menber, score);
    }

    @Override
    public Double increment(String key, String menber, double delta) {
        return stringRedisTemplate.opsForZSet().incrementScore(key, menber, delta);
    }

    @Override
    public Double zscore(String key, String menber) {
        return stringRedisTemplate.opsForZSet().score(key, menber);
    }

    @Override
    public Long zrem(String key, Object... menbers) {
        return stringRedisTemplate.opsForZSet().remove(key, menbers);
    }
}
