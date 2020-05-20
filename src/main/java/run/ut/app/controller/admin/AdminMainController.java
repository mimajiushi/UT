package run.ut.app.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import run.ut.app.api.admin.AdminMainControllerApi;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.vo.SystemInfoVO;
import run.ut.app.security.CheckAuthorization;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * this is a route controller
 * @author Lucien
 * @version 1.0
 * @date 2020/4/5 16:53
 */

@Controller
@RequestMapping("admin")
public class AdminMainController implements AdminMainControllerApi {
    /**
     * 登录页
     * @return template path
     */
    @GetMapping("login")
    public String login() {
        return "common/login";
    }

    /**
     * 首页
     * @return template path
     */
    @GetMapping({"index", "/"})
    public String index() {
        return "common/main";
    }

    /**
     * 欢迎页
     * @return template path
     */
    @GetMapping("welcome")
    public String welcome() {
        return "common/welcome";
    }

    /**
     * 审核列表
     * @return template path
     */
    @GetMapping("verifyList")
    public String verifyList() {
        return "user/verify_list";
    }

    /**
     * 标签列表
     * @return template path
     */
    @GetMapping("tagsList")
    public String tagsList() {
        return "tags/tags_list";
    }

    /**
     * 查看日志
     * @return template path
     */
    @GetMapping("log")
    public String log() {
        return "system/system_log";
    }

    /**
     * 活动列表
     * @return template path
     */
    @GetMapping("activityList")
    public String activityList() {
        return "activity/activity_list";
    }

    /**
     * 发布活动
     * @return template path
     */
    @GetMapping("activityAdd")
    public String activityAdd() {
        return "activity/activity_add";
    }

    /**
     * 更新活动
     * @return template path
     */
    @GetMapping("activityEdit/{activityId:\\d+}")
    public String activityEdit(@PathVariable Long activityId) {
        return "activity/activity_edit";
    }

    @ResponseBody
    @GetMapping("systemInfo")
    @CheckAuthorization(roles = UserRolesEnum.ROLE_ADMIN)
    public BaseResponse<SystemInfoVO> systemInfo(HttpServletRequest request) {
        Properties properties = System.getProperties();
        SystemInfoVO systemInfo = new SystemInfoVO();
        systemInfo.setAvailableProcessors(Runtime.getRuntime().availableProcessors() + "核")
                .setJavaVersion(properties.getProperty("java.version"))
                .setJvmName(properties.getProperty("java.vm.name"))
                .setOsArch(properties.getProperty("os.arch"))
                .setOsName(properties.getProperty("os.name"))
                .setOsVersion(properties.getProperty("os.version"))
                .setServerInfo(request.getServletContext().getServerInfo());
        return BaseResponse.ok(systemInfo);
    }
}
