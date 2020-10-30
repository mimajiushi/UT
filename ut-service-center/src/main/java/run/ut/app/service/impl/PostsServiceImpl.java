package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import run.ut.app.config.redis.RedisKey;
import run.ut.app.event.LikesEvent;
import run.ut.app.exception.AlreadyExistsException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.*;
import run.ut.app.model.domain.*;
import run.ut.app.model.elasticsearch.ESPosts;
import run.ut.app.model.enums.LikesTypeEnum;
import run.ut.app.model.enums.PostQuerySortEnum;
import run.ut.app.model.param.PostParam;
import run.ut.app.model.param.SearchPostParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.PostVO;
import run.ut.app.service.PostsService;
import run.ut.app.service.RedisService;
import run.ut.app.utils.BeanUtils;

import java.util.ArrayList;
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
@DubboService
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

    private final UserPostsMapper userPostsMapper;
    private final RedisService redisService;
    private final PostsMapper postsMapper;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final ForumMapper forumMapper;
    private final PostCommentsMapper postCommentsMapper;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

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
        String key1 = String.format(RedisKey.USER_LIKE_POST, uid, postId);
        String key2 = String.format(RedisKey.POST_LIKE_COUNT, postId);
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
        String key1 = String.format(RedisKey.USER_LIKE_POST, uid, postId);
        String key2 = String.format(RedisKey.POST_LIKE_COUNT, postId);
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
//        Posts post = getById(postId);
//        if (ObjectUtils.isEmpty(post)) {
//            throw new BadRequestException("Bad request!");
//        }
        int delete = userPostsMapper.delete(new QueryWrapper<UserPosts>()
            .eq("post_id", postId).eq("uid", uid));
        return delete > 0 ? BaseResponse.ok("取消收藏成功~") : BaseResponse.ok("取消失败！");
    }

    @Override
    @Deprecated
    public CommentPage<PostVO> listPostsByParams(SearchPostParam searchPostParam, Page page) {
        Long operatorUid = searchPostParam.getOperatorUid();

        Long forumId = searchPostParam.getForumId();
        Forum forum = null;
        if (null != forumId) {
            forum = forumMapper.selectById(forumId);
        }
        if (forumId != null && ObjectUtils.isEmpty(forum)) {
            throw new NotFoundException("版块不存在！");
        }

        IPage<PostVO> postVOIPage = postsMapper.listPostsByParams(page, searchPostParam);
        long total = postVOIPage.getTotal();

        List<PostVO> postVOS1 = postVOIPage.getRecords();
        postVOS1 = setCountAndIsLike(postVOS1, operatorUid);

        if (!ObjectUtils.isEmpty(forum)) {
            final Forum finalForum = forum;
            postVOS1 = postVOS1.stream().map(e -> {
                return e.setForumName(finalForum.getName())
                    .setForumId(finalForum.getId());
            }).collect(Collectors.toList());
        }

        return new CommentPage<>(total, postVOS1);
    }

    @Override
    public CommentPage<PostVO> listPostsByParams(SearchPostParam searchPostParam) {

        Integer pageNum = searchPostParam.getPageNum();
        Integer pageSize = searchPostParam.getPageSize();
        Integer sortType = searchPostParam.getSortType();
        Boolean desc = searchPostParam.getDesc();

        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        PostQuerySortEnum sortEnum = PostQuerySortEnum.getByType(sortType);

        String keyword = searchPostParam.getKeyword();

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withPageable(pageRequest);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // bool query filter
        if (searchPostParam.getForumId() != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery("forum_id", searchPostParam.getForumId()));
        }
        if (searchPostParam.getUid() != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery("uid", searchPostParam.getOperatorUid()));
        }
        nativeSearchQueryBuilder.withFilter(boolQueryBuilder);

        if (StringUtils.isNoneBlank(keyword)) {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("title", keyword),
                ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("content", keyword),
                ScoreFunctionBuilders.weightFactorFunction(5)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders =
                new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .setMinScore(2);
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        }
        // 搜索默认按相关度倒序排
        if (StringUtils.isNoneBlank(keyword) && ObjectUtils.isEmpty(sortEnum)) {
            nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(desc ? SortOrder.DESC : SortOrder.ASC));
        } else {
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortEnum.getColumn()).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }
        // 查询转换
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        SearchHits<ESPosts> searchHits = elasticsearchRestTemplate.search(searchQuery, ESPosts.class);
        if (searchHits.getTotalHits() <= 0) {
            return CommentPage.emptyPage();
        } else {
            List<ESPosts> esPosts = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
            List<PostVO> postVOS = trans2PostVOs(esPosts);
            postVOS = setCountAndIsLike(postVOS, searchPostParam.getOperatorUid());
            return new CommentPage<>(searchHits.getTotalHits(), postVOS);
        }
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

        String key = String.format(RedisKey.POST_READ_COUNT, postId);
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
        String key = String.format(RedisKey.POST_LIKE_COUNT, postId);
        String res = redisService.get(key);
        if (StringUtils.isBlank(res)) {
            return 0L;
        }
        return Long.valueOf(res);
    }

    @Override
    @NonNull
    public Long getPostReadCount(Long postId) {
        String key = String.format(RedisKey.POST_READ_COUNT, postId);
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
            postVO.setCommentCount(postCommentsMapper.selectCount(new QueryWrapper<PostComments>().eq("post_id", postVO.getId())));
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
        String key = String.format(RedisKey.USER_LIKE_POST, uid, postId);
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

    private List<PostVO> trans2PostVOs(List<ESPosts> esPosts) {
        if (CollectionUtils.isEmpty(esPosts)) {
            return new ArrayList<>();
        }
       return esPosts.stream().map(esPost -> {
            PostVO postVO = new PostVO();
            BeanUtils.updateProperties(esPost, postVO);
            User user = userMapper.selectById(postVO.getUid());
            Forum forum = forumMapper.selectById(postVO.getForumId());
            postVO.setNickname(user.getNickname())
                .setAvatar(user.getAvatar())
                .setForumName(forum.getName());
            return postVO;
        }).collect(Collectors.toList());
    }
}
