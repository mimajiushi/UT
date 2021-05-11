package run.ut.app.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
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
public enum MsgReadStatusEnum implements IEnum<Integer>, Serializable {
    UN_READ(0, "未读"),
    READ(1, "已读");

    private int type;

    @JsonValue
    private String name;

    MsgReadStatusEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }


    public static MsgReadStatusEnum getByType(int type) {
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static MsgReadStatusEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
