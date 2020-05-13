package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.ut.app.config.redis.RedisConfig;
import run.ut.app.exception.BadRequestException;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.PostsMapper;
import run.ut.app.model.domain.PostPhotos;
import run.ut.app.model.domain.Posts;
import run.ut.app.model.domain.UserPosts;
import run.ut.app.model.param.PostParam;
import run.ut.app.model.param.SearchPostParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.vo.PostVO;
import run.ut.app.service.PostPhotosService;
import run.ut.app.service.PostsService;
import run.ut.app.service.RedisService;
import run.ut.app.service.UserPostsService;

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

    private final PostPhotosService postPhotosService;
    private final UserPostsService userPostsService;
    private final RedisService redisService;

    @Override
    @Transactional
    public BaseResponse<String> savePost(PostParam postParam) {
        boolean insert = (null == postParam.getId());
        List<String> photos = postParam.getPhotos();
        if (insert) {
            Posts posts = postParam.convertTo();
            save(posts);
            List<PostPhotos> postPhotosList = photos.stream().map(url ->
                new PostPhotos().setPostId(posts.getId()).setPhoto(url)).collect(Collectors.toList());
            postPhotosService.saveBatch(postPhotosList);
        } else {
            Posts posts = getById(postParam.getId());
            if (ObjectUtils.isEmpty(posts)) {
                throw new NotFoundException("帖子不存在！");
            }
            postParam.update(posts);
            posts.setUpdateTime(null);
            postPhotosService.remove(new QueryWrapper<PostPhotos>().eq("post_id", posts.getId()));
            List<PostPhotos> postPhotosList = photos.stream().map(url ->
                new PostPhotos().setPostId(posts.getId()).setPhoto(url)).collect(Collectors.toList());
            updateById(posts);
            postPhotosService.saveBatch(postPhotosList);
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
        postPhotosService.remove(new QueryWrapper<PostPhotos>().eq("post_id", postId));
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
            throw new BadRequestException("已经点赞过了");
        }
        redisService.set(key1, "1");
        redisService.increment(key2, 1);
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
        return BaseResponse.ok("取消成功~");
    }

    @Override
    public BaseResponse<String> collect(Long postId, Long uid) {
        Posts post = getById(postId);
        if (ObjectUtils.isEmpty(post)) {
            throw new BadRequestException("收藏的帖子不存在！");
        }
        UserPosts userPosts = new UserPosts().setPostId(postId).setUid(uid);
        userPostsService.save(userPosts);
        return BaseResponse.ok("收藏成功~");
    }

    @Override
    public BaseResponse<String> cancelCollect(Long postId, Long uid) {
        Posts post = getById(postId);
        if (ObjectUtils.isEmpty(post)) {
            throw new BadRequestException("Bad request!");
        }
        userPostsService.remove(new QueryWrapper<UserPosts>()
            .eq("post_id", postId).eq("uid", uid));
        return BaseResponse.ok("取消成功~");
    }

    @Override
    public List<PostVO> listPostsByParams(SearchPostParam searchPostParam, Page page) {
        // TODO 写sql
        return null;
    }
}
