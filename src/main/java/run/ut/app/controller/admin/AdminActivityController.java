package run.ut.app.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    /**
     * todo 保存分类也需要带上分类
     */
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

    @PostMapping("delClassify/{id:\\d+}")
    @Override
    public BaseResponse<String> delClassify(@PathVariable Long id) {
        return activityClassifyService.delClassify(id);
    }
}
