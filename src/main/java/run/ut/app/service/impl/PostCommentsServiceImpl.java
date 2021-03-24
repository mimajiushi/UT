package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import run.ut.app.config.redis.RedisKey;
import run.ut.app.event.CommentEvent;
import run.ut.app.event.LikesEvent;
import run.ut.app.exception.AlreadyExistsException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.PostCommentsMapper;
import run.ut.app.mapper.PostsMapper;
import run.ut.app.model.domain.PostComments;
import run.ut.app.model.domain.Posts;
import run.ut.app.model.domain.User;
import run.ut.app.model.enums.LikesTypeEnum;
import run.ut.app.model.param.CommentParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.ChildCommentVO;
import run.ut.app.model.vo.ParentCommentVO;
import run.ut.app.service.PostCommentsService;
import run.ut.app.service.RedisService;
import run.ut.app.service.UserService;
import run.ut.app.utils.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

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
    private final UserService userService;
    private final PostCommentsMapper postCommentsMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public BaseResponse<String> commentPost(CommentParam commentParam) {
        Posts posts = postsMapper.selectById(commentParam.getPostId());
        if (ObjectUtils.isEmpty(posts)) {
            throw new NotFoundException("帖子不存在");
        }
        PostComments postComments = BeanUtils.transformFrom(commentParam, PostComments.class);
        posts.setUpdateTime(null);
        postsMapper.updateById(posts);
        save(postComments);

        eventPublisher.publishEvent(new CommentEvent(this, commentParam));

        return BaseResponse.ok("评论成功~");
    }

    @Override
    @Transactional
    public BaseResponse<String> replyToComments(CommentParam commentParam) {
        Posts posts = postsMapper.selectById(commentParam.getPostId());
        int count2 = count(new QueryWrapper<PostComments>().eq("post_id", commentParam.getPostId()));
        if (ObjectUtils.isEmpty(posts) || count2 < 1) {
            throw new NotFoundException("帖子不存在");
        }
        PostComments postComments = BeanUtils.transformFrom(commentParam, PostComments.class);
        save(postComments);
        posts.setUpdateTime(null);
        postsMapper.updateById(posts);

        eventPublisher.publishEvent(new CommentEvent(this, commentParam));

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
        PostComments postComments = getById(commentId);
        if (ObjectUtils.isEmpty(postComments)) {
            throw new NotFoundException("评论不存在");
        }
        String key = String.format(RedisKey.USER_LIKE_COMMENT, uid, commentId);
        String res = redisService.get(key);
        if (!StringUtils.isBlank(res)) {
            throw new AlreadyExistsException("点赞过了哦~");
        }
        redisService.set(key, "1");
        key = String.format(RedisKey.COMMENT_LIKE_COUNT, commentId);
        redisService.increment(key, 1);

        // publish event
        eventPublisher.publishEvent(new LikesEvent(this, commentId, LikesTypeEnum.LIKE_COMMENT));

        return BaseResponse.ok("点赞成功~");
    }

    @Override
    public BaseResponse<String> cancelLikesComment(Long uid, Long commentId) {
        String key = String.format(RedisKey.USER_LIKE_COMMENT, uid, commentId);
        String res = redisService.get(key);
        if (StringUtils.isBlank(res)) {
            throw new BadRequestException("无法取消！");
        }
        redisService.remove(key);
        key = String.format(RedisKey.COMMENT_LIKE_COUNT, commentId);
        redisService.increment(key, -1);

        // publish event
        eventPublisher.publishEvent(new LikesEvent(this, commentId, LikesTypeEnum.UN_LIKE_COMMENT));

        return BaseResponse.ok("取消点赞~");
    }

    @Override
    public Page<PostComments> listParentCommentsByPostId(Page<PostComments> page, Long postId) {
        return page(page, new QueryWrapper<PostComments>()
            .eq("post_id", postId)
            .eq("parent_comment_id", 0)
            .orderByDesc("likes", "create_time"));
    }

    @Override
    public CommentPage<ParentCommentVO> listCommentOfPost(Page<PostComments> page, Long postId, Long operatorUid) {
        // list parent comments
        Page<PostComments> postCommentsPage = listParentCommentsByPostId(page, postId);
        long total = postCommentsPage.getTotal();
        Set<Long> uids = new HashSet<>(1 << 8);
        List<Long> parentCommentIds = new ArrayList<>();
        List<ParentCommentVO> parentCommentVOList = postCommentsPage.getRecords()
            .stream().map(e -> {
                uids.add(e.getFromUid());
                parentCommentIds.add(e.getId());
                return (ParentCommentVO) new ParentCommentVO().convertFrom(e);
            }).collect(Collectors.toList());
        if (postCommentsPage.getTotal() == 0) {
            return new CommentPage<>(total, new ArrayList<>());
        }

        // list child comments
        List<PostComments> childComment = list(new QueryWrapper<PostComments>()
            .in("parent_comment_id", parentCommentIds).orderByDesc("create_time"));
        List<ChildCommentVO> childCommentVOList = childComment.stream().map(e ->
            BeanUtils.transformFrom(e, ChildCommentVO.class)).collect(Collectors.toList());
        // group by parentCommentId
        Map<Long, List<ChildCommentVO>> childCommentMap = childCommentVOList.stream().collect(Collectors.groupingBy(ChildCommentVO::getParentCommentId));
        childCommentMap.forEach((k, v) -> {
            v.forEach(e -> {
                uids.add(e.getFromUid());
                uids.add(e.getToUid());
                e.setLikes(getCommentLikeCount(e.getId()))
                .setLike(isLikeComment(operatorUid, e.getId()));
            });
        });

        // list user and then transform map
        Map<Long, User> userHashMap = userService.listByIds(uids).stream().collect(Collectors.toMap(User::getUid, e -> e));

        // set nickname、avatar、child comments and so on.
        childCommentMap.forEach((k, v) -> {
            v.forEach(e -> {
                User fromUser = userHashMap.get(e.getFromUid());
                User toUser = userHashMap.get(e.getToUid());
                e.setFromNickname(fromUser.getNickname())
                    .setToNickname(toUser.getNickname())
                    .setFromAvatar(fromUser.getAvatar());
            });
        });

        parentCommentVOList.forEach(e -> {
            User user = userHashMap.get(e.getFromUid());
            e.setNickname(user.getNickname())
                .setAvatar(user.getAvatar())
                .setLikes(getCommentLikeCount(e.getId()))
                .setLike(isLikeComment(operatorUid, e.getId()))
                .setChildComments(childCommentMap.get(e.getId()));
        });

        return new CommentPage<>(postCommentsPage.getTotal(), parentCommentVOList);
    }

    @Override
    public void incrementLikesCount(Long commentId, Integer delta) {
        postCommentsMapper.incrementLikesCount(commentId, delta);
    }


    @Override
    public Long getCommentLikeCount(Long commentId) {
        String key = String.format(RedisKey.COMMENT_LIKE_COUNT, commentId);
        String res = redisService.get(key);
        if (StringUtils.isBlank(res)) {
            return 0L;
        }
        return Long.valueOf(res + "");
    }

    @Override
    public CommentPage<ChildCommentVO> listCommentToSelf(Long uid, Page<PostComments> page) {
        Page<PostComments> postCommentsPage = page(page, new QueryWrapper<PostComments>().eq("to_uid", uid).orderByDesc("create_time"));
        List<PostComments> postCommentsList = postCommentsPage.getRecords();

        if (CollectionUtils.isEmpty(postCommentsList)) {
            return new CommentPage<>(postCommentsPage.getTotal(), new ArrayList<>());
        }

        HashSet<Long> uids = new HashSet<>(postCommentsList.size());
        postCommentsList.forEach(e -> uids.add(e.getFromUid()));

        Map<Long, User> userMap = userService.listByIds(uids).stream().collect(Collectors.toMap(User::getUid, e -> e));

        List<ChildCommentVO> childCommentVOList = postCommentsList.stream().map(e -> {
            ChildCommentVO childCommentVO = BeanUtils.transformFrom(e, ChildCommentVO.class);
            User user = userMap.get(e.getFromUid());
            if (!ObjectUtils.isEmpty(user)) {
                assert childCommentVO != null;
                childCommentVO.setFromNickname(user.getNickname())
                    .setFromAvatar(user.getAvatar());
            }
            return childCommentVO;
        }).collect(Collectors.toList());


        return new CommentPage<>(postCommentsPage.getTotal(), childCommentVOList);
    }

    @Override
    public CommentPage<ParentCommentVO> listCommentToSelfPost(Long uid, Page<PostComments> page) {
        IPage<ParentCommentVO> parentCommentVOIPage = baseMapper.listCommentToSelfPost(page, uid);
        return new CommentPage<>(parentCommentVOIPage.getTotal(), parentCommentVOIPage.getRecords());
    }

    @Override
    public List<Integer> getCommentUnreadCount(Long uid) {
        Integer[] counts = new Integer[]{0, 0};
        String key = String.format(RedisKey.USER_UNREAD_COUNT_POST, uid);
        String value = redisService.get(key);
        if (!StringUtils.isBlank(value)) {
            counts[0] = Integer.valueOf(value);
        }
        key = String.format(RedisKey.USER_UNREAD_COUNT_PARENT_COMMENT, uid);
        value = redisService.get(key);
        if (!StringUtils.isBlank(value)) {
            counts[1] = Integer.valueOf(value);
        }
        return Arrays.asList(counts);
    }

    @Override
    public BaseResponse<String> clearUnreadCount(Long uid) {
        String key = String.format(RedisKey.USER_UNREAD_COUNT_POST, uid);
        redisService.remove(key);
        key = String.format(RedisKey.USER_UNREAD_COUNT_PARENT_COMMENT, uid);
        redisService.remove(key);
        return BaseResponse.ok("ok");
    }

    private boolean isLikeComment(Long uid, Long commentId) {
        String key = String.format(RedisKey.USER_LIKE_COMMENT, uid, commentId);
        String res = redisService.get(key);
        return !StringUtils.isBlank(res);
    }
}
