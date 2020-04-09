package run.ut.app.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.support.BaseResponse;

@Api(value = "查看应用日志API",tags = "查看应用日志API")
public interface LogControllerApi {

    @ApiOperation("查看SpringBoot应用控制台日志")
    String getSpringLogsFiles(Long lines);
}
