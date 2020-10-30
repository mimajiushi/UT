package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import run.ut.app.model.param.SearchPostParam;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.PostVO;

/**
 * @author wenjie
 */
@Api(value = "BBS数据加载API",tags = "BBS数据加载API")
public interface PostIndexControllerApi {

    @ApiOperation(value = "获取首页帖子列表", notes = "按更新时间（回复时间）倒序，需要传入token判断用户是否点赞帖子")
    CommentPage<PostVO> listPosts(SearchPostParam searchPostParam);

    @ApiOperation(value = "帖子详情", notes = "需要传入token判断用户是否点赞、收藏帖子。在请求完这个接口之后再请求评论接口")
    PostVO postDetail(@PathVariable Long postId);

}
