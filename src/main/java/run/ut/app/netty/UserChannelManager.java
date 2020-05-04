package run.ut.app.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import run.ut.app.config.redis.RedisConfig;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;
import run.ut.app.model.support.WebSocketMsg;
import run.ut.app.service.RedisService;
import run.ut.app.utils.JsonUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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

    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private final RedisService redisService;

    private final Long TTL = 60L * 60L;

    /**
     * Save the mapping of uid and channel
     *
     * @param uid        uid
     * @param channel    channel
     */
    public void add(@NonNull Long uid, @NonNull Channel channel) {
        String key = RedisConfig.CHANNEL_KEY + "::" + channel.id().asLongText();
        String value = uid + "";
        redisService.setKeyValTTL(key, value, TTL);
        userChannelMap.put(uid, channel);
        channels.add(channel);
    }

    /**
     * Remove the element by uid
     *
     * @param uid    uid
     */
    public void remove(Long uid) {
        Channel channel = userChannelMap.remove(uid);
        redisService.remove(RedisConfig.CHANNEL_KEY + "::" + channel.id().asLongText());
    }

    /**
     *  Remove the cache by channel
     *
     * @param channel    channel
     */
    public void remove(Channel channel) {
        String longText = channel.id().asLongText();
        String uid = redisService.get(RedisConfig.CHANNEL_KEY + "::" + longText);
        if (!StringUtils.isBlank(uid)) {
            redisService.remove(RedisConfig.CHANNEL_KEY + "::" + longText);
            userChannelMap.remove(Long.valueOf(uid));
        }
    }

    /**
     * Remove the cache by channel longId
     * @param longText Channel long Id (channel.id().asLongText())
     */
    public void remove(String longText) {
        String uid = redisService.get(RedisConfig.CHANNEL_KEY + "::" + longText);
        if (!StringUtils.isBlank(uid)) {
            redisService.remove(RedisConfig.CHANNEL_KEY + "::" + longText);
            userChannelMap.remove(Long.valueOf(uid));
        }
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
     * Get channel by channelId
     * @param channelId   channel Id
     * @return channel
     */
    public Channel get(ChannelId channelId) {
        return channels.find(channelId);
    }

    /**
     * Clear cache
     */
    public void clearAll() {
        userChannelMap.clear();
        channels.clear();
    }


    /**
     * Write and flush by uid
     *
     * @param uid      uid
     * @param msgObj   msg object, it will be automatically converted to json.
     * @throws JsonProcessingException If msgObj fails to convert to json.
     */
    public void writeAndFlush(Long uid, Object msgObj, WebSocketMsgTypeEnum typeEnum) throws JsonProcessingException {
        Channel channel = userChannelMap.get(uid);
        boolean active = channel.isActive();
        if (active) {
            WebSocketMsg webSocketMsg = new WebSocketMsg()
                .setType(typeEnum.getType())
                .setMsg(msgObj);
            String json = JsonUtils.objectToJson(webSocketMsg);
            TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
            channel.writeAndFlush(textWebSocketFrame);
        } else {
            redisService.remove(RedisConfig.CHANNEL_KEY + "::" + channel.id().asLongText());
            userChannelMap.remove(uid);
        }
    }

    /**
     * Write and flush to every user
     * @param msgObj msg object, it will be automatically converted to json.
     * @return ChannelGroupFuture
     * @throws JsonProcessingException If msgObj fails to convert to json.
     */
    public ChannelGroupFuture writeAndFlush(Object msgObj, WebSocketMsgTypeEnum typeEnum) throws JsonProcessingException {
        WebSocketMsg webSocketMsg = new WebSocketMsg()
            .setType(typeEnum.getType())
            .setMsg(msgObj);
        String json = JsonUtils.objectToJson(webSocketMsg);
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
        return channels.writeAndFlush(textWebSocketFrame);
    }

    public void reSetCache() {
        Iterator<Map.Entry<Long, Channel>> iterator = userChannelMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Channel> cur = iterator.next();
            Long uid = cur.getKey();
            Channel channel = cur.getValue();

            String key = RedisConfig.CHANNEL_KEY + "::" + channel.id().asLongText();

            if (channel.isActive()) {
                redisService.expire(key, TTL, TimeUnit.SECONDS);
            } else {
                iterator.remove();
                remove(uid);
            }
        }
    }
}
