package run.ut.app.config.redis;

/**
 * redis相关配置
 */
public class RedisKey {

    /**
     * Expiration time of SMS in seconds
     */
    public static final long SMS_TIME_OUT = 300;

    public static final long EMAIL_CODE_TIME_OUT = 600;

    /**
     * Expiration time of area in seconds
     */
    public static final long AREA_TTL = 60 * 60 * 24 * 7;

    /**
     * Email
     */
    public static final String EMAIL_LOGIN_PREFIX = "EMAIL_LOGIN";
    public static final String USER_EMAIL = "USER_EMAIL_CODE::%s::%s";

    public static final String AREA_PREFIX = "AREA_DATA";
    public static final String AREA_INFO_PREFIX = "AREA_INFO_DATA";

    public static final String SCHOOL_DATA_LIST_PREFIX = "SCHOOL_DATA_LIST";
    public static final String SCHOOL_DATA_PREFIX = "SCHOOL_DATA";

    public static final String OPTIONS_KEY = "options";

    /**
     * post cache
     */
    public static final String USER_LIKE_POST = "USER::%s::LIKE_POST::%s";
    public static final String POST_LIKE_COUNT = "POST_LIKE_COUNT::%s";
    public static final String POST_READ_COUNT = "POST_READ_COUNT::%s";

    /**
     * comment cache
     */
    public static final String USER_LIKE_COMMENT = "USER::%s::LIKE_COMMENT::%s";
    public static final String COMMENT_LIKE_COUNT = "COMMENT_LIKE_COUNT::%s";

    public static final String USER_UNREAD_COUNT_POST = "USER_UNREAD_COUNT_POST::%s";
    public static final String USER_UNREAD_COUNT_PARENT_COMMENT = "USER_UNREAD_COUNT_PARENT_COMMENT::%s";

    /**
     * activity cache
     */
    public static final String ACTIVITY_READ_COUNT = "ACTIVITY_READ_COUNT::%s";

}
