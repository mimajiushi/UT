package run.ut.app.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import run.ut.app.model.support.AuthorizeRoles;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.security.CheckAuthorization;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * this is a route controller
 * @author Lucien
 * @version 1.0
 * @date 2020/4/5 16:53
 */

@Controller
@RequestMapping("admin")
public class AdminMainController {
    /**
     * 登录页
     * @return template path
     */
    @RequestMapping("login")
    public String login() {
        return "login";
    }

    /**
     * 首页
     * @return template path
     */
    @RequestMapping("index")
    public String index() {
        return "index";
    }

    /**
     * 欢迎页
     * @return template path
     */
    @RequestMapping("welcome")
    public String welcome() {
        return "welcome";
    }

    /**
     * 获取系统信息
     * @return System Information Json
     */
    @ResponseBody
    @GetMapping("systemInfo")
    @CheckAuthorization(roles = AuthorizeRoles.ROLE_ADMIN)
    public BaseResponse<Map<String, String>> systemInfo(HttpServletRequest request) {
        Properties properties = System.getProperties();
        Map<String, String> map = new HashMap<>();
        map.put("java", properties.getProperty("java.version"));
        map.put("osArch", properties.getProperty("os.arch"));
        map.put("osVersion", properties.getProperty("os.version"));
        map.put("JVM", properties.getProperty("java.vm.name"));
        map.put("osName", properties.getProperty("os.name"));
        map.put("server", request.getServletContext().getServerInfo());
        map.put("cpu", Runtime.getRuntime().availableProcessors() + "核");
        return BaseResponse.ok(map);
    }
}
