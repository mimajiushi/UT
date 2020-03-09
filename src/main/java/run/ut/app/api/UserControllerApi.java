package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.param.UserInfoParam;
import run.ut.app.model.param.UserParam;
import run.ut.app.model.support.BaseResponse;


@Api(value="用户信息API",tags = "用户操作API", description = "用户登录、注册、完善信息、更改信息等操作")
public interface UserControllerApi {

    @ApiOperation(value = "用户登录（网页端）", notes = "用户首次登录即可自动注册，无需手动注册")
    public UserDTO webPageLogin(UserParam userParam);

    @ApiOperation("发送短信验证码接口")
    public BaseResponse<String> sendSms(String phoneNumber);

    @ApiOperation(value = "认证申请", notes = "role字段说明：3-学生认证  5-导师认证  6-赛事主办方认证")
    public BaseResponse<String>  applyForCertification(UserInfoParam userInfoParam);

}
