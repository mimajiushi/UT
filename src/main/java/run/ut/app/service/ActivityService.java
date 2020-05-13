package run.ut.app.service;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.domain.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.dto.ActivityDTO;
import run.ut.app.model.param.ActivityParam;

/**
 * <p>
 *  ActivityService
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
public interface ActivityService extends IService<Activity> {

    /**
     * create activity
     *
     * @param activityParam activity info
     * @param cover cover photo (MultipartFile)
     * @return ActivityDTO
     */
    ActivityDTO createActivity(@NonNull ActivityParam activityParam, @NonNull MultipartFile cover);
}
