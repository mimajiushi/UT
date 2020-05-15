package run.ut.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.ut.app.handler.FileHandlers;
import run.ut.app.model.domain.Activity;
import run.ut.app.mapper.ActivityMapper;
import run.ut.app.model.dto.ActivityDTO;
import run.ut.app.model.param.ActivityParam;
import run.ut.app.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
    @Transactional
    public ActivityDTO createActivity(ActivityParam activityParam) {

        // save activity
        Activity activity = new Activity();
        activity.setContent(activityParam.getContent()).setTitle(activityParam.getTitle())
                .setStartTime(activityParam.getStartTime()).setEndTime(activityParam.getEndTime())
                .setCover(activityParam.getCover());

        save(activity);
        return new ActivityDTO().convertFrom(activity);
    }
}
