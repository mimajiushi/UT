package run.ut.app.listener;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import run.ut.app.config.redis.RedisConfig;
import run.ut.app.event.options.OptionsUpdatedEvent;
import run.ut.app.service.RedisService;

/**
 * Option updated event listener.
 *
 * @author wenjie
 * @date 2020-4-6
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OptionsUpdatedEventListener {

    private final RedisService redisService;

    @EventListener
    public void handleOptionsUpdatedEvent(OptionsUpdatedEvent optionsUpdatedEvent) {
        redisService.remove(RedisConfig.OPTIONS_KEY);
    }
}
