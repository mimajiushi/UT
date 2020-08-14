package run.ut.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import run.ut.app.model.domain.User;
import run.ut.app.model.param.SearchStudentParam;
import run.ut.app.model.vo.StudentVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenjie
 * @since 2020-03-09
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<StudentVO> listStudentByParam(Page page, @Param("stp")SearchStudentParam searchStudentParam);

}
