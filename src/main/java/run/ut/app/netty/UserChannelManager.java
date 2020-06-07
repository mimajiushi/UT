package run.ut.app.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;
import run.ut.app.model.support.WebSocketMsg;
import run.ut.app.utils.JsonUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * It is used to manage the mapping between user and channel.
 *
 * @author wenjie
 */

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserChannelManager {

    private ConcurrentHashMap<Long, Channel> userChannelMap = new ConcurrentHashMap<>(1 << 8);

    /**
     * Save the mapping of uid and channel
     *
     * @param uid        uid
     * @param channel    channel
     */
    public void add(@NonNull Long uid, @NonNull Channel channel) {
        userChannelMap.put(uid, channel);
    }

    /**
     * Remove the element by uid
     *
     * @param uid    uid
     */
    public void remove(Long uid) {
        userChannelMap.remove(uid);
    }

    /**
     *  Remove the cache by channel
     *
     * @param channel    channel
     */
    public void remove(Channel channel) {
        userChannelMap.entrySet().stream().filter(entry -> entry.getValue() == channel)
            .forEach(entry -> userChannelMap.remove(entry.getKey()));
    }

    /**
     * Get channel by uid
     * @param uid     uid
     * @return channel
     */
    public Channel get(Long uid) {
        return userChannelMap.get(uid);
    }

    /**
     * Clear cache
     */
    public void clearAll() {
        userChannelMap.clear();
    }


    /**
     * Write and flush by uid
     *
     * @param uid      uid
     * @param msgObj   msg object, it will be automatically converted to json.
     * @throws JsonProcessingException If msgObj fails to convert to json.
     */
    @Async
    public void writeAndFlush(Long uid, Object msgObj, WebSocketMsgTypeEnum typeEnum) throws JsonProcessingException {
        Channel channel = userChannelMap.get(uid);
        if (channel.isActive()) {
            WebSocketMsg webSocketMsg = new WebSocketMsg()
                .setType(typeEnum.getType())
                .setMsg(msgObj);
            String json = JsonUtils.objectToJson(webSocketMsg);
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
            channel.writeAndFlush(textWebSocketFrame);
        }
    }

    /**
     * Write and flush to every user
     * @param msgObj msg object, it will be automatically converted to json.
     * @throws JsonProcessingException If msgObj fails to convert to json.
     */
    @Async
    public void writeAndFlush(Object msgObj, WebSocketMsgTypeEnum typeEnum) throws JsonProcessingException {
        WebSocketMsg webSocketMsg = new WebSocketMsg()
            .setType(typeEnum.getType())
            .setMsg(msgObj);
        String json = JsonUtils.objectToJson(webSocketMsg);
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
        userChannelMap.forEach((uid, channel) -> {
            if (channel.isActive()) {
                channel.writeAndFlush(textWebSocketFrame);
            }
        });
    }
}
