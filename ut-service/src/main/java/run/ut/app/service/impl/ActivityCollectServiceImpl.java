package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import run.ut.app.exception.AlreadyExistsException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.ActivityCollectMapper;
import run.ut.app.mapper.ActivityMapper;
import run.ut.app.model.domain.Activity;
import run.ut.app.model.domain.ActivityCollect;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.ActivityCollectService;

/**
 * <p>
 *  ActivityCollectServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActivityCollectServiceImpl extends ServiceImpl<ActivityCollectMapper, ActivityCollect> implements ActivityCollectService {

    private final ActivityMapper activityMapper;

    @Override
    public BaseResponse<String> collectActivity(Long uid, Long activityId) {
        Activity activity = activityMapper.selectById(activityId);
        if (ObjectUtils.isEmpty(activity)) {
            throw new NotFoundException("活动不存在");
        }
        int count = count(new QueryWrapper<ActivityCollect>().eq("uid", uid).eq("activity_id", activityId));
        if (count > 0) {
            throw new AlreadyExistsException("已经收藏过了~");
        }
        ActivityCollect activityCollect = new ActivityCollect().setUid(uid).setActivityId(activityId);
        save(activityCollect);
        return BaseResponse.ok("收藏成功~");
    }

    @Override
    public BaseResponse<String> cancelCollectActivity(Long uid, Long activityId) {
//        Activity activity = activityMapper.selectById(activityId);
//        if (ObjectUtils.isEmpty(activity)) {
//            throw new NotFoundException("活动不存在");
//        }
        int count = count(new QueryWrapper<ActivityCollect>().eq("uid", uid).eq("activity_id", activityId));
        if (count < 1) {
            throw new BadRequestException("没有收藏记录");
        }
        remove(new QueryWrapper<ActivityCollect>().eq("uid", uid).eq("activity_id", activityId));
        return BaseResponse.ok("取消收藏成功");
    }
}
