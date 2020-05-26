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
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.ut.app.config.redis.RedisConfig;
import run.ut.app.event.LikesEvent;
import run.ut.app.exception.AlreadyExistsException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.ForumMapper;
import run.ut.app.mapper.PostsMapper;
import run.ut.app.mapper.UserMapper;
import run.ut.app.mapper.UserPostsMapper;
import run.ut.app.model.domain.Forum;
import run.ut.app.model.domain.Posts;
import run.ut.app.model.domain.User;
import run.ut.app.model.domain.UserPosts;
import run.ut.app.model.enums.LikesTypeEnum;
import run.ut.app.model.param.PostParam;
import run.ut.app.model.param.SearchPostParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.PostVO;
import run.ut.app.service.PostsService;
import run.ut.app.service.RedisService;
import run.ut.app.utils.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  PostsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

    private final UserPostsMapper userPostsMapper;
    private final RedisService redisService;
    private final PostsMapper postsMapper;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final ForumMapper forumMapper;

    @Override
    @Transactional
    public BaseResponse<String> savePost(PostParam postParam) {
        boolean insert = (null == postParam.getId());
        Forum forum = forumMapper.selectById(postParam.getForumId());
        if (ObjectUtils.isEmpty(forum)) {
            throw new BadRequestException("版块不存在！");
        }
        if (insert) {
            Posts posts = postParam.convertTo();
            save(posts);
        } else {
            Posts posts = getById(postParam.getId());
            if (ObjectUtils.isEmpty(posts)) {
                throw new NotFoundException("帖子不存在！");
            }
            postParam.update(posts);
            posts.setUpdateTime(null);
            updateById(posts);
        }
        return BaseResponse.ok("发布成功~");
    }

    @Override
    @Transactional
    public BaseResponse<String> delPost(Long postId, Long uid) {
        Posts post = getById(postId);
        if (ObjectUtils.isEmpty(post) || !post.getUid().equals(uid)) {
            throw new BadRequestException("你没有权限删除或帖子不存在！");
        }
        removeById(postId);
        return BaseResponse.ok("删除成功~");
    }

    @Override
    public BaseResponse<String> like(Long postId, Long uid) {
        Posts post = getById(postId);
        if (ObjectUtils.isEmpty(post)) {
            throw new BadRequestException("帖子不存在！");
        }
        String key1 = String.format(RedisConfig.USER_LIKE_POST, uid, postId);
        String key2 = String.format(RedisConfig.POST_LIKE_COUNT, postId);
        String res = redisService.get(key1);
        if (!StringUtils.isBlank(res)) {
            throw new AlreadyExistsException("已经点赞过了");
        }
        redisService.set(key1, "1");
        redisService.increment(key2, 1);

        // publish event
        eventPublisher.publishEvent(new LikesEvent(this, postId, LikesTypeEnum.LIKE_POST));

        return BaseResponse.ok("点赞成功~");
    }

    @Override
    public BaseResponse<String> unLike(Long postId, Long uid) {
        Posts post = getById(postId);
        if (ObjectUtils.isEmpty(post)) {
            throw new BadRequestException("帖子不存在！");
        }
        String key1 = String.format(RedisConfig.USER_LIKE_POST, uid, postId);
        String key2 = String.format(RedisConfig.POST_LIKE_COUNT, postId);
        String res = redisService.get(key1);
        if (StringUtils.isBlank(res)) {
            throw new BadRequestException("Bad request!");
        }
        redisService.remove(key1);
        redisService.increment(key2, -1);

        // publish event
        eventPublisher.publishEvent(new LikesEvent(this, postId, LikesTypeEnum.UN_LIKE_POST));

        return BaseResponse.ok("取消成功~");
    }

    @Override
    public BaseResponse<String> collect(Long postId, Long uid) {
        Posts post = getById(postId);
        if (ObjectUtils.isEmpty(post)) {
            throw new BadRequestException("收藏的帖子不存在！");
        }
        Integer count = userPostsMapper.selectCount(new QueryWrapper<UserPosts>().eq("uid", uid).eq("post_id", postId));
        if (count > 0) {
            throw new AlreadyExistsException("已经收藏过了~");
        }
        UserPosts userPosts = new UserPosts().setPostId(postId).setUid(uid);

        userPostsMapper.insert(userPosts);
        return BaseResponse.ok("收藏成功~");
    }

    @Override
    public BaseResponse<String> cancelCollect(Long postId, Long uid) {
        Posts post = getById(postId);
        if (ObjectUtils.isEmpty(post)) {
            throw new BadRequestException("Bad request!");
        }
        userPostsMapper.delete(new QueryWrapper<UserPosts>()
            .eq("post_id", postId).eq("uid", uid));
        return BaseResponse.ok("取消成功~");
    }

    @Override
    public CommentPage<PostVO> listPostsByParams(SearchPostParam searchPostParam, Page page) {
        Long operatorUid = searchPostParam.getOperatorUid();

        Long forumId = searchPostParam.getForumId();
        Forum forum = forumMapper.selectById(forumId);

        IPage<PostVO> postVOIPage = postsMapper.listPostsByParams(page, searchPostParam);
        long total = postVOIPage.getTotal();

        List<PostVO> postVOS1 = postVOIPage.getRecords();
        List<PostVO> postVOS2 = setCountAndIsLike(postVOS1, operatorUid).stream().map(e -> {
             return e.setForumName(forum.getName()).setForumId(forum.getId());
        }).collect(Collectors.toList());

        return new CommentPage<>(total, postVOS2);
    }

    @Override
    public CommentPage<PostVO> listCollectionByParams(Page page, SearchPostParam searchPostParam) {
        Long uid = searchPostParam.getUid();

        IPage<PostVO> postVOIPage = postsMapper.listCollectionByParams(page, searchPostParam);
        long total = postVOIPage.getTotal();

        List<PostVO> postVOS1 = postVOIPage.getRecords();
        List<PostVO> postVOS2 = setCountAndIsLike(postVOS1, uid);
        return new CommentPage<>(total, postVOS2);
    }

    @Override
    public PostVO postDetail(Long operatorUid, Long postId) {
        // Get post
        Posts post = getById(postId);
        if (ObjectUtils.isEmpty(post)) {
            throw new NotFoundException("帖子不存在");
        }

        PostVO postVO = BeanUtils.transformFrom(post, PostVO.class);
        setCountAndIsLikeAndIsCollect(postVO, operatorUid);

        User user = userMapper.selectById(post.getUid());
        postVO.setNickname(user.getNickname()).setAvatar(user.getAvatar());

        // get
        Forum forum = forumMapper.selectById(postVO.getForumId());
        postVO.setForumName(forum.getName());

        String key = String.format(RedisConfig.POST_READ_COUNT, postId);
        redisService.increment(key, 1);

        return postVO;
    }

    @Override
    public void incrementLikesCount(Long postId, Integer delta) {
        postsMapper.incrementLikesCount(postId, delta);
    }

    @Override
    @NonNull
    public Long getPostLikeCount(Long postId) {
        String key = String.format(RedisConfig.POST_LIKE_COUNT, postId);
        String res = redisService.get(key);
        if (StringUtils.isBlank(res)) {
            return 0L;
        }
        return Long.valueOf(res);
    }

    @Override
    @NonNull
    public Long getPostReadCount(Long postId) {
        String key = String.format(RedisConfig.POST_READ_COUNT, postId);
        String res = redisService.get(key);
        if (StringUtils.isBlank(res)) {
            return 0L;
        }
        return Long.valueOf(res);
    }

    private List<PostVO> setCountAndIsLike(List<PostVO> postVOList, Long uid) {
        return postVOList.stream().map(postVO -> {
            Long id = postVO.getId();
            // set count
            postVO.setLikeCount(getPostLikeCount(id)).setReadCount(getPostReadCount(id));
            // set isLike
            if (null != uid) {
                postVO.setLike(isLikePost(uid, id));
            }
            return postVO;
        }).collect(Collectors.toList());
    }

    private PostVO setCountAndIsLikeAndIsCollect(PostVO postVO, Long uid) {
        Long postId = postVO.getId();
        postVO
            .setLikeCount(getPostLikeCount(postId))
            .setReadCount(getPostReadCount(postId))
            .setLike(isLikePost(uid, postId))
            .setCollect(isCollectPost(uid, postId));
        return postVO;
    }

    private boolean isLikePost(Long uid, Long postId) {
        if (uid == null) {
            return false;
        }
        String key = String.format(RedisConfig.USER_LIKE_POST, uid, postId);
        String res = redisService.get(key);
        return !StringUtils.isBlank(res);
    }

    private boolean isCollectPost(Long uid, Long postId) {
        if (uid == null) {
            return false;
        }
        int count = userPostsMapper.selectCount(new QueryWrapper<UserPosts>().eq("uid", uid).eq("post_id", postId));
        return count > 0;
    }
}
