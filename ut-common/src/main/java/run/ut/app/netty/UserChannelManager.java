package run.ut.app.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.channel.Channel;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;

import java.util.Set;

/**
 * User - Channel manager
 *
 * @author wenjie
 * @date 2020-8-17
 */
public interface UserChannelManager {

    void add(@NonNull Long uid, @NonNull Channel channel);

    void remove(@NonNull Long uid);

    void remove(@NonNull Channel channel);

    @Nullable
    Set<Channel> get(@NonNull Long uid);

    void clearAll();

    void writeAndFlush(@NonNull Long uid, @NonNull Object msgObj, @NonNull WebSocketMsgTypeEnum typeEnum) throws JsonProcessingException;

    void writeAndFlush(@NonNull Object msgObj, @NonNull WebSocketMsgTypeEnum typeEnum) throws JsonProcessingException;
}
