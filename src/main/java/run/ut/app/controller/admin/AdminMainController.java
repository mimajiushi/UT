package run.ut.app.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
     */
    @RequestMapping("login")
    public String login() {
        return "login";
    }
}
