package run.ut.app.config.redis;

/**
 * redis相关配置
 */
public class RedisConfig {


    /**
     * Expiration time of SMS in seconds
     */
    public static final long SMS_TIME_OUT = 300;

    /**
     * Expiration time of area in seconds
     */
    public static final long AREA_TTL = 60*60*24*7;


    public static final String SMS_LOGIN_PREFIX = "SMS_LOGIN";

    public static final String AREA_PREFIX = "AREA_DATA";
    public static final String AREA_INFO_PREFIX = "AREA_INFO_DATA";

    public static final String SCHOOL_DATA_LIST_PREFIX = "SCHOOL_DATA_LIST";
    public static final String SCHOOL_DATA_PREFIX = "SCHOOL_DATA";

}
