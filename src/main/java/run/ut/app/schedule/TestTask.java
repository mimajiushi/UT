package run.ut.app.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;
import run.ut.app.netty.UserChannelManager;

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

    @Scheduled(cron = "0/10 * * * * ?")
    @Async
    public void reSetChannelCache() throws JsonProcessingException {
        userChannelManager.writeAndFlush("服务器测试心跳包", WebSocketMsgTypeEnum.KEEPALIVE);
    }

}
