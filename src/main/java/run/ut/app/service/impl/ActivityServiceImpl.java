package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import run.ut.app.config.redis.RedisConfig;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.ActivityAppointmentMapper;
import run.ut.app.mapper.ActivityCollectMapper;
import run.ut.app.mapper.ActivityMapper;
import run.ut.app.model.domain.Activity;
import run.ut.app.model.domain.ActivityAppointment;
import run.ut.app.model.domain.ActivityCollect;
import run.ut.app.model.param.ActivityParam;
import run.ut.app.model.param.SearchActivityParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.ActivityVO;
import run.ut.app.service.ActivityService;
import run.ut.app.service.RedisService;
import run.ut.app.utils.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

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

    private final RedisService redisService;
    private final ActivityAppointmentMapper activityAppointmentMapper;
    private final ActivityCollectMapper activityCollectMapper;
    private final ActivityMapper activityMapper;

    @Override
    public BaseResponse<String> saveActivity(ActivityParam activityParam) {
        Activity activity = activityParam.convertTo();
        Long activityId = activity.getId();
        if (activityId == null) {
            // insert activity
            save(activity);
            return BaseResponse.ok("活动发布成功");
        } else {
            // update activity
            activity.setUpdateTime(null);
            boolean res = updateById(activity);
            return res ? BaseResponse.ok("更新活动成功") : BaseResponse.ok("更新失败！活动可能已被删除！");
        }
    }

    @Override
    public CommentPage<ActivityVO> listActivities(Page<Activity> page, SearchActivityParam searchActivityParam) {
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "cover", "start_time", "end_time", "appointment_count", "create_time", "update_time");
        Long operatorUid = searchActivityParam.getOperatorUid();
        String title = searchActivityParam.getTitle();
        String column = searchActivityParam.getOrder();
        SearchActivityParam.OrderEnum value = SearchActivityParam.OrderEnum.getByColumn(column);
        if (!StringUtils.isBlank(title)) {
            wrapper.like("title", title);
        }
        if (null != value) {
            wrapper.orderByDesc(value.getColumn());
        } else {
            wrapper.orderByDesc("appointment_count");
        }
        Page<Activity> activityPage = page(page, wrapper);

        List<Activity> activityList = activityPage.getRecords();
        List<ActivityVO> activityVOList = activityList.stream().map(activity -> {
            ActivityVO activityVO = new ActivityVO().convertFrom(activity)
                .setReadCount(getReadCount(activity.getId()));
            if (operatorUid != null) {
                activityVO.setAppointment(isAppointment(operatorUid, activityVO.getId()));
            }
            return activityVO;
        }).collect(Collectors.toList());

        return new CommentPage<>(activityPage.getTotal(), activityVOList);
    }

    @Override
    public ActivityVO activityDetail(Long operatorUid, Long activityId) {
        Activity activity = getById(activityId);
        if (ObjectUtils.isEmpty(activity)) {
            throw new NotFoundException("找不到指定活动信息~");
        }
        ActivityVO activityVO = new ActivityVO().convertFrom(activity);
        String key = String.format(RedisConfig.ACTIVITY_READ_COUNT, activityId);
        String value = redisService.get(key);
        if (StringUtils.isBlank(value)) {
            activityVO.setReadCount(1);
        } else {
            activityVO.setReadCount(Long.valueOf(value));
        }
        redisService.increment(key, 1);
        if (operatorUid != null) {
            activityVO.setAppointment(isAppointment(operatorUid, activityId))
                .setCollect(isCollection(operatorUid, activityId));
        }
        return activityVO;
    }

    @Override
    public CommentPage<ActivityVO> listSelfCollection(Page<ActivityCollect> page, Long uid) {
        IPage<Activity> activityIPage = activityMapper.listSelfCollection(page, uid);
        List<ActivityVO> activityVOS = BeanUtils.transformFromInBatch(activityIPage.getRecords(), ActivityVO.class);
        return new CommentPage<>(activityIPage.getTotal(), activityVOS);
    }

    @Override
    public CommentPage<ActivityVO> listSelfAppointment(Page<ActivityAppointment> page, Long uid) {
        IPage<Activity> activityIPage = activityMapper.listSelfAppointment(page, uid);
        List<ActivityVO> activityVOS = BeanUtils.transformFromInBatch(activityIPage.getRecords(), ActivityVO.class);
        return new CommentPage<>(activityIPage.getTotal(), activityVOS);
    }

    private long getReadCount(Long activityId) {
        String key = String.format(RedisConfig.ACTIVITY_READ_COUNT, activityId);
        String res = redisService.get(key);
        if (StringUtils.isBlank(res)) {
            return 0L;
        }
        try {
            return Long.valueOf(res + "");
        } catch (Exception e) {
            return 0L;
        }
    }

    private boolean isAppointment(Long uid, Long activityId) {
        int count = activityAppointmentMapper.selectCount(new QueryWrapper<ActivityAppointment>()
            .eq("uid", uid)
            .eq("activity_id", activityId));
        return count > 0;
    }

    private boolean isCollection(Long uid, Long activityId) {
        int count = activityCollectMapper.selectCount(new QueryWrapper<ActivityCollect>()
            .eq("uid", uid)
            .eq("activity_id", activityId));
        return count > 0;
    }
}
