package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.param.SearchPostParam;
import run.ut.app.model.vo.PostVO;

import java.util.List;

@Api(value = "BBS数据加载API",tags = "BBS数据加载API")
public interface PostIndexControllerApi {

    @ApiOperation(value = "获取首页帖子列表", notes = "按更新时间（回复时间）")
    List<PostVO> listPosts(SearchPostParam searchPostParam, Integer pageNum, Integer pageSize);

}
