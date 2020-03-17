package run.ut.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.DataArea;

import java.util.List;

public interface DataAreaService extends IService<DataArea> {

    /**
     * 获取id下的所有地址
     * @param parentId parentId
     * @return 地址信息列表
     */
    @NonNull
    List<DataArea> getAreaDataByParentId(@NonNull Integer parentId);

    /**
     * 查询所有行政id(parent_id)
     */
    @NonNull
    List<Integer> selectParentIdDistinct();

    @NonNull
    DataArea getById(@NonNull Integer id);
}
