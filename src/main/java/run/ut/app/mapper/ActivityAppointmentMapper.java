package run.ut.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.ActivityAppointment;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
public interface ActivityAppointmentMapper extends BaseMapper<ActivityAppointment> {

    @Update("update activity set appointment_count = appointment_count + #{delta} where id = #{activityId} and deleted = 0")
    void incrAppointmentCount(@Param("activityId") @NonNull Long activityId, @Param("delta") @NonNull Integer delta);

}
