package run.ut.app.service;

import org.springframework.lang.NonNull;
import run.ut.app.model.domain.PostComments;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.param.CommentParam;
import run.ut.app.model.support.BaseResponse;

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
}
