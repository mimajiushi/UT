package run.ut.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.ActivityCollect;
import run.ut.app.model.support.BaseResponse;

/**
 * <p>
 *  ActivityCollectService
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
public interface ActivityCollectService extends IService<ActivityCollect> {

    /**
     * Collect activity
     *
     * @param uid           uid
     * @param activityId    activity id
     * @return              ok result with message
     */
    @NonNull
    BaseResponse<String> collectActivity(@NonNull Long uid, @NonNull Long activityId);

    /**
     * Cancel collect activity
     *
     * @param uid           uid
     * @param activityId    activity id
     * @return              ok result with message
     */
    @NonNull
    BaseResponse<String> cancelCollectActivity(@NonNull Long uid, @NonNull Long activityId);
}
