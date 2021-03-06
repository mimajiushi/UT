package run.ut.app.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.ut.app.api.admin.AdminActivityControllerApi;
import run.ut.app.model.dto.ActivityClassifyDTO;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.param.ActivityClassifyParam;
import run.ut.app.model.param.ActivityParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.security.CheckAuthorization;
import run.ut.app.service.ActivityClassifyService;
import run.ut.app.service.ActivityService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Lucien
 * @updater
 * @version 1.0
 * @date 2020/5/13 12:02
 */
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("admin/activity")
@CheckAuthorization(roles = {UserRolesEnum.ROLE_ADMIN, UserRolesEnum.ROLE_SPONSOR})
public class AdminActivityController implements AdminActivityControllerApi {

    private final ActivityService activityService;
    private final ActivityClassifyService activityClassifyService;

    @Override
    @PostMapping("saveActivity")
    public BaseResponse<String> saveActivity(@Valid ActivityParam activityParam) {
        return activityService.saveActivity(activityParam);
    }

    @Override
    @PostMapping("delActivity/{activityId:\\d+}")
    public BaseResponse<String> delActivity(@PathVariable Long activityId) {
        boolean res = activityService.removeById(activityId);
        return res ? BaseResponse.ok("删除成功") : BaseResponse.ok("删除失败！活动可能已被删除！");
    }

    @PostMapping("saveClassify")
    @Override
    public BaseResponse<ActivityClassifyDTO> saveClassify(@Valid ActivityClassifyParam activityClassifyParam) {
        return activityClassifyService.saveClassify(activityClassifyParam);
    }

    @PostMapping("delClassify")
    @Override
    public BaseResponse<String> delClassify(@RequestBody List<Long> ids) {
        boolean res = activityClassifyService.removeByIds(ids);
        return res ? BaseResponse.ok("删除成功") : BaseResponse.ok("删除失败，请刷新后重试");
    }
}
