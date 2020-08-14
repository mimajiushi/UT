package run.ut.app.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import run.ut.app.model.param.ForumParam;
import run.ut.app.model.support.BaseResponse;

/**
 * @author wenjie
 * @date 2020-5-19
 */

@Api(value = "管理员管理版块API",tags = "管理员管理版块API")
public interface AdminForumControllerApi {

    @ApiOperation(value = "增加/更新版块", notes = "需要管理员权限")
    BaseResponse<String> saveForum(ForumParam forumParam);

    @ApiModelProperty(value = "删除版块")
    BaseResponse<String> removeForum(@PathVariable Long forumId);
}
