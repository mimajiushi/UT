package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.param.PostParam;
import run.ut.app.model.support.BaseResponse;

@Api(value = "bbs相关API",tags = "bbs相关API")
public interface PostControllerApi {

    @ApiOperation(value = "新建/更新帖子内容")
    public BaseResponse<String> savePost(PostParam postParam);
}
