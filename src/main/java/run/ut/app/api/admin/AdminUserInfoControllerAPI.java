package run.ut.app.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestParam;
import run.ut.app.model.dto.UserInfoDTO;
import run.ut.app.model.param.UserInfoParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;

@Api(value = "管理员处理用户认证API",tags = "管理员处理用户认证API")
public interface AdminUserInfoControllerAPI {

    @ApiOperation(value = "审核用户认证资料", notes = "status: -1 - 审核不通过 1 - 审核通过")
    public BaseResponse<String> verifyUserInfo(Integer id,Integer status, String reason);

    @ApiOperation(value = "用户认证资料（用户详情）list",
            notes = "注意，字段deleted=1，表示这段数据被软删除了，用户是看不见的，目前仅支持根据审核状态查询")
    public CommentPage<UserInfoDTO> listUserInfoByParam(UserInfoParam userInfoParam,
                                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                                        @RequestParam(defaultValue = "10") Integer pageSize);
}
