package run.ut.app.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.support.BaseResponse;

@Api(value="管理员处理用户认证API",tags = "管理员处理用户认证API")
public interface AdminUserInfoControllerAPI {

    @ApiOperation(value = "审核用户认证资料", notes = "status: -1 - 审核不通过 1 - 审核通过")
    public BaseResponse<String> verifyUserInfo(Integer id,Integer status, String reason);
}
