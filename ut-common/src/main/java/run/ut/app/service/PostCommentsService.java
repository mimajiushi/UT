package run.ut.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.PostComments;
import run.ut.app.model.param.CommentParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.ChildCommentVO;
import run.ut.app.model.vo.ParentCommentVO;

import java.util.List;

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

    /**
     * Increment likes count
     *
     * @param commentId   commentId
     * @param delta        delta
     */
    void incrementLikesCount(@NonNull Long commentId, @NonNull Integer delta);

    /**
     * Get comment likes count
     *
     * @param commentId    commentId
     * @return             count
     */
    @NonNull
    Long getCommentLikeCount(@NonNull Long commentId);

    /**
     * List comments that are send to user
     *
     * @param uid   uid
     * @return      comment list
     */
    @NonNull
    CommentPage<ChildCommentVO> listCommentToSelf(@NonNull Long uid, @NonNull Page<PostComments> page);

    /**
     * List the comment of user's post
     *
     * @param uid    uid
     * @return       comment list
     */
    @NonNull
    CommentPage<ParentCommentVO> listCommentToSelfPost(@NonNull Long uid, @NonNull Page<PostComments> page);

    /**
     * Get the count of unread comment
     *
     * @param uid   uid
     * @return ↓
     *               elements[0]: count of unread comment from post
     *               elements[1]: count of unread comment from parent_comment
     */
    @NonNull
    List<Integer> getCommentUnreadCount(@NonNull Long uid);

    /**
     * Clear the cache of the unread comment count
     *
     * @param uid   uid
     * @return      ok result with message
     */
    @NonNull
    BaseResponse<String> clearUnreadCount(@NonNull Long uid);
}
