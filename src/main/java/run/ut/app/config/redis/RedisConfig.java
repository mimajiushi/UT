package run.ut.app.config.redis;

/**
 * redis相关配置
 */
public class RedisConfig {


    /**
     * Expiration time of SMS in seconds
     */
    public static final long SMS_TIME_OUT = 300;

    public static final long EMAIL_CODE_TIME_OUT = 600;

    /**
     * Expiration time of area in seconds
     */
    public static final long AREA_TTL = 60 * 60 * 24 * 7;


    public static final String SMS_LOGIN_PREFIX = "SMS_LOGIN";
    public static final String EMAIL_LOGIN_PREFIX = "EMAIL_LOGIN";

    public static final String AREA_PREFIX = "AREA_DATA";
    public static final String AREA_INFO_PREFIX = "AREA_INFO_DATA";

    public static final String SCHOOL_DATA_LIST_PREFIX = "SCHOOL_DATA_LIST";
    public static final String SCHOOL_DATA_PREFIX = "SCHOOL_DATA";

    public static final String OPTIONS_KEY = "options";

    public static final String CHANNEL_KEY = "CHANNEL_KEY";

    public static final String USER_LIKE_POST = "USER::%s::LIKE::%s";
    public static final String POST_LIKE_COUNT = "POST_LIKE_COUNT::%s";

}
