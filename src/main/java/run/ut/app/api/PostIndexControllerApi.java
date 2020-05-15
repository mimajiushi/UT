package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.param.SearchPostParam;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.PostVO;

/**
 * @author wenjie
 */
@Api(value = "BBS数据加载API",tags = "BBS数据加载API")
public interface PostIndexControllerApi {

    @ApiOperation(value = "获取首页帖子列表", notes = "按更新时间（回复时间）")
    CommentPage<PostVO> listPosts(SearchPostParam searchPostParam, Integer pageNum, Integer pageSize);

}
