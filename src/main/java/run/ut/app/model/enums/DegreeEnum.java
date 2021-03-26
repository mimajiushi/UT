package run.ut.app.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * DegreeEnum
 *
 * @author wenjie
 */
@Getter
public enum DegreeEnum implements IEnum<Integer>, Serializable {
    JUNIOR_COLLEGE(1, "大专"),
    REGULAR_COLLEGE(2, "本科"),
    DOCTOR_OR_ABOVE(3, "博士及以上");

    private int type;

    @JsonValue
    private String name;


    DegreeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }

    public static DegreeEnum getByType(int type) {
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static DegreeEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
