package run.ut.app.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.admin.AdminActivityControllerApi;
import run.ut.app.model.dto.ActivityDTO;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.param.ActivityParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.security.CheckAuthorization;
import run.ut.app.service.ActivityService;

import javax.validation.Valid;

/**
 * @author Lucien
 * @version 1.0
 * @date 2020/5/13 12:02
 */

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("admin")
@CheckAuthorization(roles = UserRolesEnum.ROLE_ADMIN)
public class AdminActivityController implements AdminActivityControllerApi {

    private final ActivityService activityService;

    @PostMapping("saveActivity")
    @Override
    public BaseResponse<ActivityDTO> createActivity(@Valid ActivityParam activityParam) {
        return BaseResponse.ok("活动发布成功！", activityService.createActivity(activityParam));
    }
}
