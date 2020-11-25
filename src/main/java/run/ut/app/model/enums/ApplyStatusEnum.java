package run.ut.app.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * ApplyStatusEnum
 *
 * @author wenjie
 */
@Getter
public enum ApplyStatusEnum implements IEnum<Integer>, Serializable {
    PASS(1, "同意"),
    WAITING(0, "处理中"),
    FAIL(-1, "拒绝");

    private int type;

    @JsonValue
    private String name;


    ApplyStatusEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }

    public static ApplyStatusEnum getByType(int type) {
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static ApplyStatusEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
