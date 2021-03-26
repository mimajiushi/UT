package run.ut.app.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Leave Mode Enum
 *
 * @author wenjie
 */
@Getter
public enum LeaveModeEnum implements IEnum<Integer>, Serializable {
    EXPEL_USER(1, "团队踢人"),
    USER_LEAVE(2, "成员主动离开");

    private int type;

    @JsonValue
    private String name;

    LeaveModeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }


    public static LeaveModeEnum getByType(int type) {
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static LeaveModeEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
