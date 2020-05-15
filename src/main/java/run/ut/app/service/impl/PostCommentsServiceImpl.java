package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.ut.app.config.redis.RedisConfig;
import run.ut.app.exception.AlreadyExistsException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.PostCommentsMapper;
import run.ut.app.mapper.PostsMapper;
import run.ut.app.model.domain.PostComments;
import run.ut.app.model.domain.Posts;
import run.ut.app.model.param.CommentParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.PostCommentsService;
import run.ut.app.service.RedisService;

/**
 * <p>
 *  PostCommentsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostCommentsServiceImpl extends ServiceImpl<PostCommentsMapper, PostComments> implements PostCommentsService {

    private final PostsMapper postsMapper;
    private final RedisService redisService;

    @Override
    public BaseResponse<String> commentPost(CommentParam commentParam) {
        int count = postsMapper.selectCount(new QueryWrapper<Posts>().eq("id", commentParam.getPostId()));
        if (count < 1) {
            throw new NotFoundException("帖子不存在");
        }
        PostComments postComments = commentParam.convertTo();
        save(postComments);
        return BaseResponse.ok("评论成功~");
    }

    @Override
    public BaseResponse<String> replyToComments(CommentParam commentParam) {
        int count1 = postsMapper.selectCount(new QueryWrapper<Posts>().eq("id", commentParam.getPostId()));
        int count2 = count(new QueryWrapper<PostComments>().eq("post_id", commentParam.getPostId()));
        if (count1 < 1 || count2 < 1) {
            throw new NotFoundException("帖子不存在");
        }
        PostComments postComments = commentParam.convertTo();
        save(postComments);
        return BaseResponse.ok("回复成功~");
    }

    @Override
    public BaseResponse<String> delComment(Long uid, Long commentId) {
        int count1 = count(new QueryWrapper<PostComments>().eq("from_uid", uid).eq("id", commentId));
        if (count1 < 1) {
            throw new NotFoundException("无法删除！");
        }
        removeById(commentId);
        return BaseResponse.ok("删除成功~");
    }

    @Override
    public BaseResponse<String> likesComment(Long uid, Long commentId) {
        String key = String.format(RedisConfig.USER_LIKE_COMMENT, uid, commentId);
        String res = redisService.get(key);
        if (!StringUtils.isBlank(res)) {
            throw new AlreadyExistsException("点赞过了哦~");
        }
        redisService.set(key, "1");
        key = String.format(RedisConfig.COMMENT_LIKE_COUNT, commentId);
        redisService.increment(key, 1);
        return BaseResponse.ok("点赞成功~");
    }

    @Override
    public BaseResponse<String> cancelLikesComment(Long uid, Long commentId) {
        String key = String.format(RedisConfig.USER_LIKE_COMMENT, uid, commentId);
        String res = redisService.get(key);
        if (StringUtils.isBlank(res)) {
            throw new BadRequestException("无法取消！");
        }
        redisService.remove(key);
        key = String.format(RedisConfig.COMMENT_LIKE_COUNT, commentId);
        redisService.increment(key, -1);
        return BaseResponse.ok("取消点赞~");
    }

    @Override
    public Long getCommentLikeCount(Long commentId) {
        String key = String.format(RedisConfig.COMMENT_LIKE_COUNT, commentId);
        String res = redisService.get(key);
        if (StringUtils.isBlank(res)) {
            return 0L;
        }
        return Long.valueOf(res + "");
    }
}
