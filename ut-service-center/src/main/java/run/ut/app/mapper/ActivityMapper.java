package run.ut.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.Activity;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    IPage<Activity> listSelfCollection(@NonNull Page page, @Param("uid") @NonNull Long uid);

    IPage<Activity> listSelfAppointment(@NonNull Page page, @Param("uid") @NonNull Long uid);
}
