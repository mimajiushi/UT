package run.ut.app.model.properties;

import lombok.Getter;

/**
 * @author wenjie
 */
@Getter
public enum  BinLogProperties implements PropertyEnum {

    BINLOG_POSITION("binlog_position", String.class, "0"),
    BINLOG_FILENAME("binlog_filename", String.class, "");

    private final String value;

    private final Class<?> type;

    private final String defaultValue;

    BinLogProperties(String value, Class<?> type, String defaultValue) {
        this.defaultValue = defaultValue;
        if (!PropertyEnum.isSupportedType(type)) {
            throw new IllegalArgumentException("Unsupported blog property type: " + type);
        }

        this.value = value;
        this.type = type;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public String defaultValue() {
        return defaultValue;
    }
}
