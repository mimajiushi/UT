package run.ut.app.service;


import run.ut.app.model.domain.DataArea;

import java.util.List;

public interface DataAreaService {

    /**
     * 获取id下的所有地址
     * @param parentId parentId
     * @return 地址信息列表
     */
    List<DataArea> getAreaDataByParentId(Integer parentId);

    /**
     * 查询所有行政id(parent_id)
     */
    List<Integer> selectParentIdDistinct();
}
