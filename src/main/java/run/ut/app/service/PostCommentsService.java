package run.ut.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.PostComments;
import run.ut.app.model.param.CommentParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.ParentCommentVO;

/**
 * <p>
 *  PostCommentsService
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
public interface PostCommentsService extends IService<PostComments> {

    /**
     * Comment post
     *
     * @param commentParam   params
     * @return               ok result with message
     */
    @NonNull
    BaseResponse<String> commentPost(@NonNull CommentParam commentParam);

    /**
     * Reply to comments
     *
     * @param commentParam   params
     * @return               ok result with message
     */
    @NonNull
    BaseResponse<String> replyToComments(@NonNull CommentParam commentParam);

    /**
     * Remove comment
     *
     * @param uid           uid
     * @param commentId     comment id
     * @return              ok result with message
     */
    @NonNull
    BaseResponse<String> delComment(@NonNull Long uid, @NonNull Long commentId);

    /**
     * Likes comment
     *
     * @param uid           uid
     * @param commentId     comment id
     * @return              ok result with message
     */
    @NonNull
    BaseResponse<String> likesComment(@NonNull Long uid, @NonNull Long commentId);

    /**
     * Cancel Likes
     *
     * @param uid           uid
     * @param commentId     commentId
     * @return              ok result with message
     */
    @NonNull
    BaseResponse<String> cancelLikesComment(@NonNull Long uid, @NonNull Long commentId);

    /**
     * List parent comments by post id (only parent comments), order by likes count
     *
     * @param page     paging object of mybatis
     * @param postId   post id
     * @return         PostComments list
     */
    @NonNull
    Page<PostComments> listParentCommentsByPostId(@NonNull Page<PostComments> page, @NonNull Long postId);

    /**
     * List comments of post
     *
     * @param page          paging object of mybatis
     * @param postId        post id
     * @param operatorUid   operator uid
     * @return              ParentCommentVO list
     */
    @NonNull
    CommentPage<ParentCommentVO> listCommentOfPost(@NonNull Page<PostComments> page, @NonNull Long postId, @NonNull Long operatorUid);

    @NonNull
    Long getCommentLikeCount(@NonNull Long commentId);
}
