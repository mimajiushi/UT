package run.ut.app.service;

import org.springframework.lang.NonNull;
import run.ut.app.model.domain.ActivityAppointment;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.support.BaseResponse;

/**
 * <p>
 *  ActivityAppointmentService
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
public interface ActivityAppointmentService extends IService<ActivityAppointment> {

    /**
     * User appointment activity
     * @param uid           uid
     * @param activityId    activity id
     * @return              ok result with message
     */
    @NonNull
    BaseResponse<String> appointment(@NonNull Long uid, @NonNull Long activityId);

    /**
     * User cancels appointment activity
     *
     * @param uid           uid
     * @param activityId    activity id
     * @return              ok result with message
     */
    @NonNull
    BaseResponse<String> cancelAppointment(@NonNull Long uid, @NonNull Long activityId);
}
