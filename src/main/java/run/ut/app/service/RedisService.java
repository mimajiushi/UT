package run.ut.app.service;


import org.springframework.lang.NonNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wenjie
 */
public interface RedisService {

    void set(String key, String value);

    public long setList(String key, List list);

    String get(String key);

    boolean setKeyValTTL(String key, String value, long ttl);

    /**
     * Time is in seconds
     */
    boolean expire(String key, long expire);

    boolean expire(String key, long expire, TimeUnit unit);

    boolean remove(String key);

    public Boolean zadd(String key, String menber, double score);

    /**
     * Zset's Increment
     */
    Double zIncrement(String key, String menber, double delta);


    Long increment(String key, int variable);

    Double zscore(String key, String menber);

    Long zrem(String key, Object... menbers);

    boolean overRequestRateLimit(@NonNull String key, final int expireTime, final int max,
                                 @NonNull TimeUnit timeUnit, String userAgent);

}
