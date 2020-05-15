package run.ut.app.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.dto.ActivityDTO;
import run.ut.app.model.param.ActivityParam;
import run.ut.app.model.support.BaseResponse;

/**
 * @author Lucien
 * @version 1.0
 * @date 2020/5/13 12:06
 */

@Api(value = "管理员活动管理相关API",tags = "管理员活动管理相关API")
public interface AdminActivityControllerApi {

    @ApiOperation(value = "管理员创建活动", notes = "需要管理员权限")
    BaseResponse<ActivityDTO> createActivity(ActivityParam activityParam);
}
