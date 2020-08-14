package run.ut.app.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import run.ut.app.event.LikesEvent;
import run.ut.app.model.enums.LikesTypeEnum;
import run.ut.app.service.PostCommentsService;
import run.ut.app.service.PostsService;

/**
 * 点赞事件监听器
 *
 * @author wenjie
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class LikesEventListener {

    private final PostCommentsService postCommentsService;
    private final PostsService postsService;

    @EventListener
    @Async
    public void handlerLikesEvent(LikesEvent likesEvent) {
        Long id = likesEvent.getId();
        LikesTypeEnum likesTypeEnum = likesEvent.getLikesTypeEnum();
        switch (likesTypeEnum) {
            case LIKE_POST:
                postsService.incrementLikesCount(id, 1);
                break;
            case LIKE_COMMENT:
                postCommentsService.incrementLikesCount(id, 1);
                break;
            case UN_LIKE_POST:
                postsService.incrementLikesCount(id, -1);
                break;
            case UN_LIKE_COMMENT:
                postCommentsService.incrementLikesCount(id, -1);
                break;
            default:
                break;
        }

    }
}
