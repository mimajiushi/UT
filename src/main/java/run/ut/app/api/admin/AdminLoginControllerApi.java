package run.ut.app.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.param.EmailLoginParam;
import run.ut.app.model.support.BaseResponse;

@Api(value = "管理员登录相关API",tags = "管理员登录相关API")
public interface AdminLoginControllerApi {


    @ApiOperation(value = "管理员邮箱登录")
    UserDTO loginByEmail(EmailLoginParam emailLoginParam);


    @ApiOperation(value = "发送登录验证码至邮箱")
    BaseResponse<String> sendEmailCode(String email);
}
