package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import run.ut.app.model.domain.TeamsMembers;
import run.ut.app.mapper.TeamsMembersMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.service.TeamsMembersService;

import java.util.List;

/**
 * <p>
 *  TeamsMembersServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-14
 */
@Service
public class TeamsMembersServiceImpl extends ServiceImpl<TeamsMembersMapper, TeamsMembers> implements TeamsMembersService {

    @Override
    public List<TeamsMembers> listByTeamsId(Long teamsId) {
        return list(new QueryWrapper<TeamsMembers>().eq("team_id", teamsId));
    }
}
