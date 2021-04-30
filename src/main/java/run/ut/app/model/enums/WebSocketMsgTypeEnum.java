package run.ut.app.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
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
public enum WebSocketMsgTypeEnum implements IEnum<Integer>, Serializable {
    RECEIVED_INVITATION(1, "收到入队邀请"),
    RECEIVED_APPLICATION(2, "收到入队申请"),
    COMMENT(3, "帖子评论/回复"),

    AUTH(1000, "身份认证"),
    KEEPALIVE(1001, "心跳包"),

    //聊天信息
    SINGLE_TEXT_MSG(2000, "单聊文本消息"),
    SINGLE_IMG_MSG(2001, "单聊图片"),

    //ACK
    SERVER_ACK(3001, "服务端返回ack"),
    CLIENT_ACK(3002, "客户端返回ack");

    private int type;

    @JsonValue
    private String name;

    WebSocketMsgTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }


    public static WebSocketMsgTypeEnum getByType(Integer type) {
        if (null == type) {
            return null;
        }
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static WebSocketMsgTypeEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
