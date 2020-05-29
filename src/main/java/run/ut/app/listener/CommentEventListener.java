package run.ut.app.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import run.ut.app.config.redis.RedisConfig;
import run.ut.app.event.CommentEvent;
import run.ut.app.model.domain.Posts;
import run.ut.app.model.param.CommentParam;
import run.ut.app.netty.UserChannelManager;
import run.ut.app.service.PostsService;
import run.ut.app.service.RedisService;

import static run.ut.app.model.enums.WebSocketMsgTypeEnum.COMMENT;

/**
 * 评论/回复事件监听器
 *
 * @author wenjie
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CommentEventListener {

    private final RedisService redisService;
    private final PostsService postsService;
    private final UserChannelManager userChannelManager;

    @EventListener
    @Async
    public void handlerCommentEvent(CommentEvent commentEvent) throws JsonProcessingException {
        CommentParam commentParam = commentEvent.getCommentParam();
        Long postId = commentParam.getPostId();
        Posts posts = postsService.getById(postId);
        Long uid = posts.getUid();
        if (commentParam.getParentCommentId() == null || commentParam.getParentCommentId() == 0) {
            String key = String.format(RedisConfig.USER_UNREAD_COUNT_POST, posts.getUid());
            redisService.increment(key, 1);
        } else {
            String key = String.format(RedisConfig.USER_UNREAD_COUNT_PARENT_COMMENT, commentParam.getToUid());
            redisService.increment(key, 1);
        }
        userChannelManager.writeAndFlush(uid, commentParam.getContent(), COMMENT);
    }
}

