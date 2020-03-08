package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.param.UserParam;
import run.ut.app.model.support.BaseResponse;


@Api(value="用户信息API",tags = "UserController", description = "用户登录、注册、完善信息、更改信息等操作")
public interface UserControllerApi {

    @ApiOperation("用户注册（网页版），只有网页版才有注册，只能使用手机号注册")
    public UserDTO webPageRegister(UserParam userParam);

    @ApiOperation("发送短信验证码接口")
    public BaseResponse<String> sendSms(String phoneNumber);

}
