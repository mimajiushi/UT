package run.ut.app.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import run.ut.app.model.dto.ActivityClassifyDTO;
import run.ut.app.model.param.ActivityClassifyParam;
import run.ut.app.model.param.ActivityParam;
import run.ut.app.model.support.BaseResponse;

/**
 * @author Lucien
 * @updater chenwenjie.star
 * @version 1.0
 * @date 2021-03-10
 */
@Api(value = "管理员活动管理相关API",tags = "管理员活动管理相关API")
public interface AdminActivityControllerApi {

    @ApiOperation(value = "管理员创建/更新活动", notes = "需要管理员权限")
    BaseResponse<String> saveActivity(ActivityParam activityParam);


    @ApiOperation(value = "删除活动")
    BaseResponse<String> delActivity(@PathVariable Long activityId);

    @ApiOperation(value = "创建/更新活动分类")
    BaseResponse<ActivityClassifyDTO> saveClassify(ActivityClassifyParam activityClassifyParam);

    @ApiOperation(value = "删除分类")
    BaseResponse<String> delClassify(@PathVariable Long classifyId);

}
