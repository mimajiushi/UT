package run.ut.app.model.properties;

/**
 * Wechat Mini Program properties
 *
 * @author wenjie
 * @date 2020-7-10
 */
public enum WechatMPProperties implements PropertyEnum {

    MP_APP_ID("mp-app-id", String.class, ""),

    MP_APP_SECRET("mp-app-secret", String.class, ""),

    MP_AUTHORIZE_URL("authorize-url", String.class, ""),

    MP_GRANT_TYPE("grant-type", String.class, "");

    private final String value;

    private final Class<?> type;

    private final String defaultValue;

    WechatMPProperties(String value, Class<?> type, String defaultValue) {
        this.value = value;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public String defaultValue() {
        return defaultValue;
    }

    @Override
    public String getValue() {
        return value;
    }
}
