package run.ut.app.netty.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import run.ut.app.exception.WebSocketException;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;
import run.ut.app.model.support.WebSocketMsg;
import run.ut.app.netty.UserChannelManager;
import run.ut.app.utils.JsonUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * It is used to manage the mapping between user and channel.
 *
 * @author wenjie
 */

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DubboService
public class UserChannelManagerImpl implements UserChannelManager {

    private ConcurrentHashMap<Long, Set<Channel>> userChannelMap = new ConcurrentHashMap<>(1 << 8);

    private final Lock lock = new ReentrantLock();

    /**
     * Save the mapping of uid and channel
     *
     * @param uid        uid
     * @param channel    channel
     */
    @Override
    public void add(@NonNull Long uid, @NonNull Channel channel) {
        try {
            lock.lock();
            Set<Channel> channels = userChannelMap.get(uid);
            if (ObjectUtils.isEmpty(channels) || channels.size() == 0) {
                Set<Channel> channelSet = new HashSet<>();
                channelSet.add(channel);
                userChannelMap.put(uid, channelSet);
            } else {
                channels.add(channel);
                userChannelMap.put(uid, channels);
            }
        } catch (Exception e) {
            throw new WebSocketException("缓存用户通道信息失败！");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Remove the element by uid
     *
     * @param uid    uid
     */
    @Override
    public void remove(@NonNull Long uid) {
        userChannelMap.remove(uid);
    }

    /**
     *  Remove the cache by channel
     *
     * @param channel    channel
     */
    @Override
    public void remove(@NonNull Channel channel) {
        userChannelMap.entrySet().stream().filter(entry -> entry.getValue().contains(channel))
            .forEach(entry -> entry.getValue().remove(channel));
    }

    /**
     * Get channel by uid
     * @param uid     uid
     * @return channel
     */
    @Nullable
    @Override
    public Set<Channel> get(@NonNull Long uid) {
        return userChannelMap.get(uid);
    }

    /**
     * Clear cache
     */
    @Override
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
    @Override
    public void writeAndFlush(@NonNull Long uid, @NonNull Object msgObj, @NonNull WebSocketMsgTypeEnum typeEnum) throws JsonProcessingException {
        Set<Channel> channelSet = userChannelMap.get(uid);
        if (ObjectUtils.isEmpty(channelSet) || channelSet.size() == 0) {
            return;
        }
        for (Channel channel : channelSet) {
            if (channel.isActive()) {
                WebSocketMsg webSocketMsg = new WebSocketMsg()
                    .setType(typeEnum.getType())
                    .setMsg(msgObj);
                String json = JsonUtils.objectToJson(webSocketMsg);
                TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
                ChannelFuture channelFuture = channel.writeAndFlush(textWebSocketFrame);
                channelFuture.addListener((ChannelFutureListener)future -> {
                    log.debug("对uid：{}, 发送websocket消息：{}", uid, json);
                });

            }
        }
    }

    /**
     * Write and flush to every user
     * @param msgObj msg object, it will be automatically converted to json.
     * @throws JsonProcessingException If msgObj fails to convert to json.
     */
    @Override
    public void writeAndFlush(@NonNull Object msgObj, @NonNull WebSocketMsgTypeEnum typeEnum) throws JsonProcessingException {
        WebSocketMsg webSocketMsg = new WebSocketMsg()
            .setType(typeEnum.getType())
            .setMsg(msgObj);
        String json = JsonUtils.objectToJson(webSocketMsg);
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
        userChannelMap.forEach((uid, channels) -> {
            for (Channel channel : channels) {
                if (channel.isActive()) {
                    ChannelFuture channelFuture = channel.writeAndFlush(textWebSocketFrame);
                    channelFuture.addListener((ChannelFutureListener)future -> {
                        log.debug("对uid：{}, 发送websocket消息：{}", uid, json);
                    });
                }
            }
        });
    }
}