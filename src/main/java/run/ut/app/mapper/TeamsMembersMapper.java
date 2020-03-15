package run.ut.app.mapper;

import org.apache.ibatis.annotations.Param;
import run.ut.app.model.domain.TeamsMembers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import run.ut.app.model.vo.TeamMemberVO;

import java.util.List;

/**
 * <p>
 *  TeamsMembersMapper
 * </p>
 *
 * @author wenjie
 * @since 2020-03-14
 */
public interface TeamsMembersMapper extends BaseMapper<TeamsMembers> {

    List<TeamMemberVO> listSimpleMemberInfoByTeamsId(@Param("teamsId") Long teamsId);

}
