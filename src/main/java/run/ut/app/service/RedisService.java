package run.ut.app.service;

import java.util.List;

/**
 * @author wenjie
 */
public interface RedisService {

    void set(String key, String value);

    public long setList(String key, List list);

    String get(String key);

    boolean setKeyValTTL(String key, String value, long ttl);

    boolean expire(String key, long expire);

    void remove(String key);

    public Boolean zadd(String key, String menber, double score);

    Double increment(String key, String menber, double delta);

    Double zscore(String key, String menber);

    Long zrem(String key, Object... menbers);

}
