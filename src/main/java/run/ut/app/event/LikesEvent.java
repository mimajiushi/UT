package run.ut.app.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import run.ut.app.model.enums.LikesTypeEnum;

/**
 * 点赞事件，mysql的持久化异步处理
 */
@Getter
public class LikesEvent extends ApplicationEvent {

    private Long id;
    private LikesTypeEnum likesTypeEnum;


    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public LikesEvent(Object source, Long id, LikesTypeEnum likesTypeEnum) {
        super(source);
        this.id = id;
        this.likesTypeEnum = likesTypeEnum;
    }
}
