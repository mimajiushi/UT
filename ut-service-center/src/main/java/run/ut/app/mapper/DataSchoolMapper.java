package run.ut.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import run.ut.app.model.domain.DataSchool;

import java.util.List;

/**
 * <p>
 * 高校数据表 Mapper 接口
 * </p>
 *
 * @author chenwenjie
 * @since 2019-10-30
 */
public interface DataSchoolMapper extends BaseMapper<DataSchool> {
    @Select("select distinct(province_id) from data_school")
    List<Integer> selectProvincIdDistinct();
}
