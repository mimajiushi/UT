package run.ut.app.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.support.BaseResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Lucien
 * @version 1.0
 * @date 2020/4/11 15:14
 */

@Api(value = "管理员前端页面相关Api", tags = "管理员前端页面相关Api")
public interface AdminMainControllerApi {

    @ApiOperation(value = "获取系统信息")
    BaseResponse<Map<String, String>> systemInfo(HttpServletRequest request);
}
