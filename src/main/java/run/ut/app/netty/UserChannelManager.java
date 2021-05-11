package run.ut.app.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import run.ut.app.exception.WebSocketException;
import run.ut.app.model.dto.ChatHistoryDTO;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;
import run.ut.app.model.support.WebSocketMsg;
import run.ut.app.netty.task.RetryTimerTask;
import run.ut.app.utils.JsonUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * It is used to manage the mapping between user and channel.
 *
 * @author wenjie
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserChannelManager {

    private ConcurrentHashMap<Long, Set<Channel>> userChannelMap = new ConcurrentHashMap<>(1 << 8);

    private final Lock lock = new ReentrantLock();
    private final Timer timer = new HashedWheelTimer();

    /**
     * Save the mapping of uid and channel
     *
     * @param uid        uid
     * @param channel    channel
     */
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
    public void remove(@NonNull Long uid) {
        userChannelMap.remove(uid);
    }

    /**
     *  Remove the cache by channel
     *
     * @param channel    channel
     */
    public void remove(@NonNull Channel channel) {
        userChannelMap.entrySet().stream().filter(entry -> entry.getValue().contains(channel))
            .forEach(entry -> entry.getValue().remove(channel));
    }

    /**
     * Get channel by uid
     * @param uid     uid
     * @return        channel
     */
    @Nullable
    public Set<Channel> get(@NonNull Long uid) {
        return userChannelMap.get(uid);
    }

    /**
     * Get channels that can be writable by uid
     *
     * @param uid    uid
     * @return       set of channel
     */
    @Nullable
    public Set<Channel> getWritAbleChannel(Long uid) {
        Set<Channel> channels = userChannelMap.get(uid);
        if (ObjectUtils.isEmpty(channels)) {
            return null;
        }
        return channels.stream().filter(Channel::isWritable).collect(Collectors.toSet());
    }

    /**
     * Clear cache
     */
    public void clearAll() {
        userChannelMap.clear();
    }


    /**
     * Write and flush by uid
     * （message delivery is not guaranteed）
     *
     * @param uid      uid
     * @param msgObj   msg object, it will be automatically converted to json.
     */
    public void writeAndFlush(@NonNull Long uid, @NonNull Object msgObj, @NonNull WebSocketMsgTypeEnum typeEnum) {
        Set<Channel> channelSet = userChannelMap.get(uid);
        if (ObjectUtils.isEmpty(channelSet) || channelSet.size() == 0) {
            return;
        }
        for (Channel channel : channelSet) {
            if (channel.isWritable()) {
                WebSocketMsg webSocketMsg = new WebSocketMsg()
                    .setType(typeEnum.getType())
                    .setMsg(msgObj);
                String json = JsonUtils.objectToJson(webSocketMsg);
                TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
                ChannelFuture channelFuture = channel.writeAndFlush(textWebSocketFrame);
                channelFuture.addListener((ChannelFutureListener)future -> {
                    log.debug("对uid：{}, 发送websocket（通知）消息：{}", uid, json);

                });

            }
        }
    }

    /**
     * Write and flush server ack
     *
     * @param chatHistoryDTO ack
     */
    public void writeAndFlushServerAck(@NonNull ChatHistoryDTO chatHistoryDTO) {
        chatHistoryDTO.setType(WebSocketMsgTypeEnum.SERVER_ACK.getType());
        Set<Channel> channelSet = userChannelMap.get(chatHistoryDTO.getFromUid());
        if (ObjectUtils.isEmpty(channelSet) || channelSet.size() == 0) {
            return;
        }
        for (Channel channel : channelSet) {
            if (channel.isWritable()) {
                String json = JsonUtils.objectToJson(chatHistoryDTO);
                TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
                ChannelFuture channelFuture = channel.writeAndFlush(textWebSocketFrame);
                channelFuture.addListener((ChannelFutureListener)future -> {
                    log.debug("对uid：{}, 发送websocket（ACK）消息：{}", chatHistoryDTO.getToUid(), json);
                });

            }
        }
    }

    /**
     * Write and flush server ack
     *
     * @param chatHistoryDTO ack
     */
    public void writeAndFlushChatMsg(@NonNull ChatHistoryDTO chatHistoryDTO) {
        Long uid = chatHistoryDTO.getToUid();
        Set<Channel> channelSet = userChannelMap.get(uid);
        if (ObjectUtils.isEmpty(channelSet) || channelSet.size() == 0) {
            return;
        }
        for (Channel channel : channelSet) {
            if (channel.isWritable()) {
                String json = JsonUtils.objectToJson(chatHistoryDTO);
                TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
                ChannelFuture channelFuture = channel.writeAndFlush(textWebSocketFrame);
                channel.attr(AttributeKey.valueOf(chatHistoryDTO.getId().toString()));
                channelFuture.addListener((ChannelFutureListener)future -> {
                    log.debug("对uid：{}, 发送websocket（聊天）消息：{}", uid, json);
                    checkAckAndResend(channel, json, chatHistoryDTO.getId(), uid);
                });

            }
        }
    }

    /**
     * Write and flush to every user
     * （message delivery is not guaranteed）
     *
     * @param msgObj msg object, it will be automatically converted to json.
     */
    public void writeAndFlush(@NonNull Object msgObj, @NonNull WebSocketMsgTypeEnum typeEnum) {
        WebSocketMsg webSocketMsg = new WebSocketMsg()
            .setType(typeEnum.getType())
            .setMsg(msgObj);
        String json = JsonUtils.objectToJson(webSocketMsg);
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(json);
        userChannelMap.forEach((uid, channels) -> {
            for (Channel channel : channels) {
                if (channel.isWritable()) {
                    ChannelFuture channelFuture = channel.writeAndFlush(textWebSocketFrame);
                    channelFuture.addListener((ChannelFutureListener)future -> {
                        log.debug("对uid：{}, 发送websocket（通知）消息：{}", uid, json);
                    });
                }
            }
        });
    }

    /**
     * check msg ack and resend msg if not ack
     */
    protected void checkAckAndResend(Channel channel, String msg, Long chatId, Long uid) {
        RetryTimerTask retryTimerTask = new RetryTimerTask(t -> {
            if (channel.attr(AttributeKey.valueOf(chatId.toString())).get() != null) {
                log.error("对uid:[{}]，发送消息后没有收到ack，尝试重发，消息id：[{}]", uid, chatId);
                channel.writeAndFlush(new TextWebSocketFrame(msg));
                log.debug("对uid：{}, 发送websocket（聊天）消息：{}", uid, msg);
            } else {
                t.cancel();
            }
        }, 5, 10);
        timer.newTimeout(retryTimerTask, 5, TimeUnit.SECONDS);
    }
}
