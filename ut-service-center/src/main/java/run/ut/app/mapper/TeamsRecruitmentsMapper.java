package run.ut.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import run.ut.app.model.domain.TeamsRecruitments;
import run.ut.app.model.param.SearchRecruitmentParam;
import run.ut.app.model.vo.TeamsRecruitmentsVO;

/**
 * <p>
 *  TeamsRecruitmentsMapper
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
public interface TeamsRecruitmentsMapper extends BaseMapper<TeamsRecruitments> {
    IPage<TeamsRecruitmentsVO> listRecruitmentByParam(Page page, @Param("srp")SearchRecruitmentParam srp);
}
