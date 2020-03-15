package run.ut.app.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

/**
 * Team's status Enum
 *
 * @author wenjie
 */
@Getter
public enum TeamsStatusEnum implements IEnum<Integer> {
    PRIVATE(0, "未在寻找团队页面展示"),
    PUBLIC(1, "已在寻找团队页面展示");

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
