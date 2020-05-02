package run.ut.app.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import run.ut.app.netty.UserChannelManager;

/**
 * Reset channel cache task
 *
 * @author wenjie
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ReSetChannelCacheTask {

    private final UserChannelManager userChannelManager;

    @Scheduled(cron = "0 */1 * * * ?")
    @Async
    public void reSetChannelCache() {
        log.debug("Reset channel cache - Starting...");
        userChannelManager.reSetCache();
        log.debug("Reset channel cache - Completed.");
    }

}
