package run.ut.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.ut.app.mapper.ActivityMapper;
import run.ut.app.model.domain.Activity;
import run.ut.app.model.param.ActivityParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.ActivityService;

/**
 * <p>
 *  ActivityServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Override
    public BaseResponse<String> saveActivity(ActivityParam activityParam) {
        Activity activity = activityParam.convertTo();
        Long activityId = activity.getId();
        if (activityId == null) {
            // insert activity
            save(activity);
        } else {
            // update activity
            activity.setUpdateTime(null);
            updateById(activity);
        }
        return BaseResponse.ok("活动发布成功");
    }
}
