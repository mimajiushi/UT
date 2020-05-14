package run.ut.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.Posts;
import run.ut.app.model.param.PostParam;
import run.ut.app.model.param.SearchPostParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.PostVO;

/**
 * <p>
 *  PostsService
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
public interface PostsService extends IService<Posts> {

    /**
     * Insert if id is empty, otherwise update.
     *
     * @param postParam   post params
     * @return            ok result with message
     */
    @NonNull
    BaseResponse<String> savePost(@NonNull PostParam postParam);

    /**
     * Delete post by id
     *
     * @param postId  post id
     * @param uid     uid
     * @return        ok result with message
     */
    @NonNull
    BaseResponse<String> delPost(@NonNull Long postId, @NonNull Long uid);

    /**
     * User likes post
     *
     * @param postId  post id
     * @param uid     uid
     * @return        ok result with message
     */
    @NonNull
    BaseResponse<String> like(@NonNull Long postId, @NonNull Long uid);

    /**
     * User cancel likes
     *
     * @param postId   post id
     * @param uid      uid
     * @return         ok result with message
     */
    @NonNull
    BaseResponse<String> unLike(@NonNull Long postId, @NonNull Long uid);

    /**
     * User collects post
     *
     * @param postId   post id
     * @param uid      uid
     * @return         ok result with message
     */
    @NonNull
    BaseResponse<String> collect(@NonNull Long postId, @NonNull Long uid);

    /**
     * User cancel collect post
     *
     * @param postId   post id
     * @param uid      uid
     * @return         ok result with message
     */
    @NonNull
    BaseResponse<String> cancelCollect(@NonNull Long postId, @NonNull Long uid);

    /**
     * Lists posts by params
     *
     * @param searchPostParam  search parameters
     * @param page             paging object of mybatis
     * @return                 PostVO list
     */
    @NonNull
    CommentPage<PostVO> listPostsByParams(@NonNull SearchPostParam searchPostParam, @NonNull Page page);

    /**
     * List collection by uid
     *
     * @param page                aging object of mybatis
     * @param searchPostParam     search parameters
     * @return      PostVO list
     */
    @NonNull
    CommentPage<PostVO> listCollectionByParams(@NonNull Page page, @NonNull SearchPostParam searchPostParam);

    @NonNull
    Long getPostLikeCount(@NonNull Long postId);

    @NonNull
    Long getPostReadCount(@NonNull Long postId);
}
