package run.ut.app.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.admin.LogControllerApi;
import run.ut.app.controller.BaseController;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.AdminService;

@RestController
@Slf4j
@RequestMapping("admin/log")
public class LogController extends BaseController implements LogControllerApi {

    @DubboReference
    private AdminService adminService;

    /**
     * TODO check admin
     */
    @Override
    @GetMapping(value = "ut/spring_log_file")
    public BaseResponse<String> getSpringLogsFiles(@RequestParam(name = "lines", defaultValue = "200") Long lines) {
        return BaseResponse.ok(adminService.getSpringLogsFiles(lines));
    }
}
