package run.ut.app.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Team's status Enum
 *
 * @author wenjie
 */
@Getter
public enum TeamsStatusEnum implements IEnum<Integer>, Serializable {
    /**
     * 不招人
     */
    PRIVATE(0, "不招募"),
    /**
     * 招人
     */
    PUBLIC(1, "招募中");

    private int type;

    @JsonValue
    private String name;

    TeamsStatusEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }


    public static TeamsStatusEnum getByType(int type) {
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static TeamsStatusEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
