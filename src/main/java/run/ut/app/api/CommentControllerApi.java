package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import run.ut.app.model.param.CommentParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.ChildCommentVO;
import run.ut.app.model.vo.ParentCommentVO;

import java.util.List;

/**
 * @author wenjie
 */
@Api(value = "帖子评论操作API",tags = "帖子评论操作API")
public interface CommentControllerApi {

    @ApiOperation(value = "评论帖子")
    BaseResponse<String> commentPost(CommentParam commentParam);

    @ApiOperation(value = "回复评论")
    BaseResponse<String> replyToComments(CommentParam commentParam);

    @ApiOperation(value = "删除评论/回复")
    BaseResponse<String> delComment(@PathVariable Long commentId);

    @ApiOperation(value = "点赞评论")
    BaseResponse<String> likesComment(@PathVariable Long commentId);

    @ApiOperation(value = "取消点赞")
    BaseResponse<String> cancelLikesComment(@PathVariable Long commentId);

    @ApiOperation(value = "获取帖子下的评论", notes = "如果需要判断用户是否收藏、点赞，请传token")
    CommentPage<ParentCommentVO> listCommentOfPost(@PathVariable Long postId, Integer pageNum, Integer pageSize);

    @ApiOperation(value = "用户查询别人对自己的回复", notes = "需要传token，帖子的父评论不包括在内，帖子回复是另一个接口")
    CommentPage<ChildCommentVO> listCommentToSelf(Integer pageNum, Integer pageSize);

    @ApiOperation(value = "用户查看自己帖子有哪些回复", notes = "需要传token，仅父评论，父评论下的子评论是另一个接口")
    CommentPage<ParentCommentVO> listCommentToSelfPost(Integer pageNum, Integer pageSize);

    @ApiOperation(value = "获取未读评论/回复消息数量", notes = "index=0的元素为帖子评论未读，index=1的元素为回复未读")
    List<Integer> getCommentUnreadCount();
}
