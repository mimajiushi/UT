package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.ut.app.exception.AlreadyExistsException;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.ActivityMapper;
import run.ut.app.model.domain.Activity;
import run.ut.app.model.domain.ActivityAppointment;
import run.ut.app.mapper.ActivityAppointmentMapper;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.ActivityAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  ActivityAppointmentServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActivityAppointmentServiceImpl extends ServiceImpl<ActivityAppointmentMapper, ActivityAppointment> implements ActivityAppointmentService {

    private final ActivityAppointmentMapper activityAppointmentMapper;
    private final ActivityMapper activityMapper;

    @Override
    @Transactional
    public BaseResponse<String> appointment(Long uid, Long activityId) {
        Activity activity = activityMapper.selectById(activityId);
        if (ObjectUtils.isEmpty(activity)) {
            throw new NotFoundException("活动不存在！");
        }
        int count = count(new QueryWrapper<ActivityAppointment>().eq("uid", uid).eq("activity_id", activityId));
        if (count > 0) {
            throw new AlreadyExistsException("已经预约过了哦~");
        }
        ActivityAppointment activityAppointment = ActivityAppointment.builder().uid(uid).activityId(activityId).build();
        boolean save = save(activityAppointment);
        activityAppointmentMapper.incrAppointmentCount(activityId, 1);
        return save ? BaseResponse.ok("预约成功！") : BaseResponse.ok("请稍后再试");
    }

    @Override
    @Transactional
    public BaseResponse<String> cancelAppointment(Long uid, Long activityId) {
        int count = count(new QueryWrapper<ActivityAppointment>().eq("uid", uid).eq("activity_id", activityId));
        if (count < 1) {
            throw new NotFoundException("取消失败，没有预约记录~");
        }
        boolean remove = remove(new QueryWrapper<ActivityAppointment>().eq("uid", uid).eq("activity_id", activityId));
        activityAppointmentMapper.incrAppointmentCount(activityId, -1);
        return remove ? BaseResponse.ok("取消成功~") : BaseResponse.ok("请稍后尝试~");
    }
}
