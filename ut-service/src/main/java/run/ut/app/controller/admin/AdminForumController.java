package run.ut.app.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.admin.AdminForumControllerApi;
import run.ut.app.controller.BaseController;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.param.ForumParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.security.CheckAuthorization;
import run.ut.app.service.ForumService;

/**
 * @author wenjie
 * @date 2020-5-19
 */

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("admin/forum")
@CheckAuthorization(roles = UserRolesEnum.ROLE_ADMIN)
public class AdminForumController extends BaseController implements AdminForumControllerApi {

    private final ForumService forumService;

    @Override
    @PostMapping("saveForum")
    public BaseResponse<String> saveForum(ForumParam forumParam) {
        return forumService.saveForum(forumParam);
    }

    @Override
    @PostMapping("removeForum/{forumId:\\d+}")
    public BaseResponse<String> removeForum(@PathVariable Long forumId) {
        return forumService.removeForum(forumId);
    }
}
