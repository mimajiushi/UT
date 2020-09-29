package run.ut.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.DataSchool;

import java.util.List;

/**
 * @author wenjie
 */

public interface DataSchoolService extends IService<DataSchool> {

    /**
     * 根据行政id查询对应区的学校信息列表
     *
     * @param provinceId 行政区id
     * @return 返回行政区下学校信息列表
     */
    @NonNull
    List<DataSchool> getListByProvinceId(@NonNull Integer provinceId);

    /**
     * 获取学校行政区id (province_id)，会去重
     *
     * @return id列表
     */
    @NonNull
    List<Integer> selectProvincIdDistinct();

    /**
     * 根据id获取学校信息
     *
     * @return 学校信息
     */
    @NonNull
    DataSchool getById(@NonNull Integer id);

    /**
     * 获取所有学校信息
     * @return 学校信息列表
     */
    @NonNull
    List<DataSchool> getAllSchool();

}
