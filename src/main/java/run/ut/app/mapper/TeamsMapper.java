package run.ut.app.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import run.ut.app.model.domain.Teams;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import run.ut.app.model.param.SearchTeamParam;
import run.ut.app.model.vo.TeamVO;

/**
 * <p>
 *  TeamsMapper
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
public interface TeamsMapper extends BaseMapper<Teams> {

    IPage<TeamVO> listTeamByParam(Page page, @Param("stp") SearchTeamParam searchTeamParam);

}
