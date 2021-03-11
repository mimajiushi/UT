package run.ut.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.ActivityClassify;
import run.ut.app.model.dto.ActivityClassifyDTO;
import run.ut.app.model.param.ActivityClassifyParam;
import run.ut.app.model.support.BaseResponse;

import java.util.List;

/**
 * <p>
 * 活动分类 服务类
 * </p>
 *
 * @author chenwenjie.star
 * @since 2021-03-10
 */
public interface ActivityClassifyService extends IService<ActivityClassify> {

    /**
     * 获取所有活动分类
     */
    List<ActivityClassifyDTO> getAllClassify();

    /**
     * 添加分类/更新分类
     *
     * @param param 分类参数
     */
    @NonNull
    BaseResponse<ActivityClassifyDTO> saveClassify(@NonNull ActivityClassifyParam param);
}
