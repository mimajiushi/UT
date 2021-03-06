package run.ut.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.ut.app.api.ActivityControllerApi;
import run.ut.app.model.domain.Activity;
import run.ut.app.model.domain.ActivityAppointment;
import run.ut.app.model.domain.ActivityCollect;
import run.ut.app.model.dto.ActivityClassifyDTO;
import run.ut.app.model.param.SearchActivityParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.ActivityVO;
import run.ut.app.security.CheckLogin;
import run.ut.app.service.ActivityAppointmentService;
import run.ut.app.service.ActivityClassifyService;
import run.ut.app.service.ActivityCollectService;
import run.ut.app.service.ActivityService;

import java.util.List;

/**
 * @author wenjie
 * @date 2020-5-17
 */
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("activity")
public class ActivityController extends BaseController implements ActivityControllerApi {

    private final ActivityService activityService;
    private final ActivityCollectService activityCollectService;
    private final ActivityAppointmentService activityAppointmentService;
    private final ActivityClassifyService activityClassifyService;

    @Override
    @GetMapping("list/activities")
    public CommentPage<ActivityVO> listActivities(SearchActivityParam searchActivityParam,
                                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                                  @RequestParam(defaultValue = "5") Integer pageSize) {
        searchActivityParam.setOperatorUid(getUidFromToken());
        Page<Activity> page = new Page<>(pageNum, pageSize);

        return activityService.listActivities(page, searchActivityParam);
    }

    @Override
    @GetMapping("detail/{activityId:\\d+}")
    public ActivityVO activityDetail(@PathVariable Long activityId) {
        return activityService.activityDetail(getUidFromToken(), activityId);
    }

    @Override
    @PostMapping("collect/{activityId:\\d+}")
    @CheckLogin
    public BaseResponse<String> collectActivity(@PathVariable Long activityId) {
        return activityCollectService.collectActivity(getUid(), activityId);
    }

    @Override
    @PostMapping("collect_cancel/{activityId:\\d+}")
    @CheckLogin
    public BaseResponse<String> cancelCollectActivity(@PathVariable Long activityId) {
        return activityCollectService.cancelCollectActivity(getUid(), activityId);
    }

    @Override
    @PostMapping("appointment/{activityId:\\d+}")
    @CheckLogin
    public BaseResponse<String> appointment(@PathVariable Long activityId) {
        return activityAppointmentService.appointment(getUid(), activityId);
    }

    @Override
    @PostMapping("cancelAppointment/{activityId:\\d+}")
    @CheckLogin
    public BaseResponse<String> cancelAppointment(@PathVariable Long activityId) {
        return activityAppointmentService.cancelAppointment(getUid(), activityId);
    }

    @Override
    @GetMapping("list/self/collections")
    @CheckLogin
    public CommentPage<ActivityVO> listSelfCollection(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ActivityCollect> page = new Page<>(pageNum, pageSize);
        return activityService.listSelfCollection(page, getUid());
    }

    @Override
    @GetMapping("list/self/appointments")
    @CheckLogin
    public CommentPage<ActivityVO> listSelfAppointment(@RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ActivityAppointment> page = new Page<>(pageNum, pageSize);
        return activityService.listSelfAppointment(page, getUid());
    }

    @GetMapping("list/activity/classify")
    @Override
    public List<ActivityClassifyDTO> classifyList() {
        return activityClassifyService.getAllClassify();
    }
}
