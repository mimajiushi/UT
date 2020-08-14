package run.ut.app.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

/**
 * UserInfoStatusEnum
 *
 * @author wenjie
 */
@Getter
public enum UserInfoStatusEnum implements IEnum<Integer> {
    PASS(1, "审核通过"),
    WAITING(0, "审核中"),
    FAIL(-1, "审核不通过");

    private int type;

    @JsonValue
    private String name;


    UserInfoStatusEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }

    public static UserInfoStatusEnum getByType(int type) {
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static UserInfoStatusEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
