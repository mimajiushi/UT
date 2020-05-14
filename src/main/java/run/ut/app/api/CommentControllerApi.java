package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.param.CommentParam;
import run.ut.app.model.support.BaseResponse;

/**
 * @author wenjie
 */
@Api(value = "帖子评论操作API",tags = "帖子评论操作API")
public interface CommentControllerApi {

    @ApiOperation(value = "评论帖子")
    BaseResponse<String> commentPost(CommentParam commentParam);

    @ApiOperation(value = "回复评论")
    BaseResponse<String> replyToComments(CommentParam commentParam);

}
