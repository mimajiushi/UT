package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import run.ut.app.model.param.PostParam;
import run.ut.app.model.support.BaseResponse;

@Api(value = "bbs相关API",tags = "bbs相关API")
public interface PostControllerApi {

    @ApiOperation(value = "新建/更新帖子内容")
    BaseResponse<String> savePost(PostParam postParam);

    @ApiOperation(value = "删除帖子", notes = "用户可删除自己发的帖子")
    BaseResponse<String> delPost(@PathVariable Long postId);

    @ApiOperation(value = "帖子点赞")
    BaseResponse<String> like(@PathVariable Long postId);

    @ApiOperation(value = "帖子取消点赞")
    BaseResponse<String> unLike(@PathVariable Long postId);

    @ApiOperation(value = "收藏帖子")
    BaseResponse<String> collect(@PathVariable Long postId);

    @ApiOperation(value = "取消收藏")
    BaseResponse<String> cancelCollect(@PathVariable Long postId);
}
