package run.ut.app.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * ApplyModeEnum
 *
 * @author wenjie
 */
@Getter
public enum ApplyModeEnum implements IEnum<Integer>, Serializable {
    USER_TO_TEAM(1, "用户申请加入团队"),
    TEAM_TO_USER(2, "团队邀请用户加入");

    private int type;

    @JsonValue
    private String name;

    ApplyModeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }


    public static ApplyModeEnum getByType(int type) {
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static ApplyModeEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
