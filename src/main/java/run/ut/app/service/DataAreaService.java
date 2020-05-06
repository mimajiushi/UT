package run.ut.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.DataArea;

import java.util.List;

/**
 * @author wenjie
 */

public interface DataAreaService extends IService<DataArea> {

    /**
     * 获取id下的所有地址
     * @param parentId parentId
     * @return 地址信息列表
     */
    @NonNull
    List<DataArea> getAreaDataByParentId(@NonNull Integer parentId);


    /**
     * 查询所有行政id（parent_id）
     * @return 行政id列表
     */
    @NonNull
    List<Integer> selectParentIdDistinct();

    /**
     * 根据id获取行政区信息
     * @param id 区域id
     * @return 区域信息
     */
    @NonNull
    DataArea getById(@NonNull Integer id);
}
