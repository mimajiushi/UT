package run.ut.app.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import run.ut.app.model.dto.ChatHistoryDTO;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;
import run.ut.app.netty.UserChannelManager;
import run.ut.app.service.RedisService;

/**
 * Reset channel cache task
 *
 * @author wenjie
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TestTask {

    private final UserChannelManager userChannelManager;
    private final RedisService redisService;

    @Scheduled(cron = "0/10 * * * * ?")
    @Async
    public void heartBeat() throws JsonProcessingException {
        userChannelManager.writeAndFlush("服务器测试心跳包", WebSocketMsgTypeEnum.KEEPALIVE);
    }

    /**
     * 便于测试
     */
    @Scheduled(cron = "0/1 * * * * ?")
    @Async
    public void adminLoginCache() {
        String key = "ADMIN_EMAIL_LOGIN::1498780478@qq.com";
        String value = "123456";
        redisService.set(key, value);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    @Async
    public void chatMsgReSend() throws JsonProcessingException {
        userChannelManager.writeAndFlushChatMsg(
                new ChatHistoryDTO().setId(1L).setToUid(21L).setContent("test")
        );
    }


}
