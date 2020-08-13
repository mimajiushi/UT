package run.ut.app.event.options;

import org.springframework.context.ApplicationEvent;

/**
 * Option updated event.
 *
 * @author wenjie
 * @date 2020-4-6
 */
public class OptionsUpdatedEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OptionsUpdatedEvent(Object source) {
        super(source);
    }
}
