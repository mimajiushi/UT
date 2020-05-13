package run.ut.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.exception.BadRequestException;
import run.ut.app.handler.FileHandlers;
import run.ut.app.model.domain.Activity;
import run.ut.app.mapper.ActivityMapper;
import run.ut.app.model.dto.ActivityDTO;
import run.ut.app.model.param.ActivityParam;
import run.ut.app.model.support.UploadResult;
import run.ut.app.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.utils.ImageUtils;

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

    private final ActivityMapper activityMapper;
    private final FileHandlers fileHandlers;

    @Override
    @Transactional
    public ActivityDTO createActivity(ActivityParam activityParam, MultipartFile cover) {

        // upload cover
        UploadResult uploadResult = null;
        boolean hasCover = !ObjectUtils.isEmpty(cover);
        if (hasCover) {
            if (!ImageUtils.isImage()) {
                throw new BadRequestException("只接受图片格式文件！");
            }
            uploadResult = fileHandlers.upload(cover);
        }

        // save activity
        Activity activity = new Activity();
        activity.setContent(activityParam.getContent()).setTitle(activityParam.getTitle())
                .setStartTime(activityParam.getStartTime()).setEndTime(activityParam.getEndTime());

        if (hasCover) {
            activity.setCover(uploadResult.getFilePath());
        }
        save(activity);
        return new ActivityDTO().convertFrom(activity);
    }
}
