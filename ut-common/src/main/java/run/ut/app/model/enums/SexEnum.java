package run.ut.app.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * sex enum
 *
 * @author wenjie
 */
@Getter
public enum SexEnum implements IEnum<Integer>, Serializable {
    UNKNOW(0, "保密"),
    MALE(1, "男"),
    FEMALE(2, "女");

    private int type;

    @JsonValue
    private String name;

    SexEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }


    public static SexEnum getByType(Integer type) {
        if (null == type) {
            return null;
        }
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static SexEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
