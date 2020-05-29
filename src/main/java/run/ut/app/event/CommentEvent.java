package run.ut.app.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import run.ut.app.model.param.CommentParam;

/**
 * comment event
 * @author wenjie
 */
@Getter
public class CommentEvent extends ApplicationEvent {

    private CommentParam commentParam;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public CommentEvent(Object source, CommentParam commentParam) {
        super(source);
        this.commentParam = commentParam;
    }
}
