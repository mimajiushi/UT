package run.ut.app.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Team's member role Enum
 *
 * @author wenjie
 */
@Getter
public enum TeamsMemberEnum implements IEnum<Integer>, Serializable {
    NORMAL(0, "普通成员"),
    LEADER(1, "队长");

    private int type;

    @JsonValue
    private String name;

    TeamsMemberEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }


    public static TeamsMemberEnum getByType(int type) {
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static TeamsMemberEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
