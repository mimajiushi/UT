package run.ut.app.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

/**
 * sex enum
 *
 * @author wenjie
 */
@Getter
public enum SexEnum implements IEnum<Integer> {
    UNKNOW(-1 , "保密"),
    MALE(1, "男"),
    FEMALE(0, "女");

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


    public static SexEnum getByType(int type) {
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static SexEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
