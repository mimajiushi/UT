package run.ut.app.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author wenjie
 */
@Getter
public enum GetChatMsgTypeEnum implements IEnum<Integer>, Serializable {
    HISTORY(-1, "历史消息"),
    NEW(1, "新消息");

    private int type;

    private String name;

    GetChatMsgTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }


    public static GetChatMsgTypeEnum getByType(Integer type) {
        if (null == type) {
            return null;
        }
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static GetChatMsgTypeEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
