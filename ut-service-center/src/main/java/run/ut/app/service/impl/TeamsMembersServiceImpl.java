package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.UserMapper;
import run.ut.app.model.domain.TeamsMembers;
import run.ut.app.mapper.TeamsMembersMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.model.domain.User;
import run.ut.app.model.enums.TeamsMemberEnum;
import run.ut.app.service.TeamsMembersService;

import javax.validation.constraints.NotNull;
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
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DubboService
public class TeamsMembersServiceImpl extends ServiceImpl<TeamsMembersMapper, TeamsMembers> implements TeamsMembersService {

    private final UserMapper userMapper;

    @Override
    public List<TeamsMembers> listByTeamsId(@NotNull Long teamsId) {
        return list(new QueryWrapper<TeamsMembers>().eq("team_id", teamsId));
    }

    @Override
    public Integer countByUid(@NotNull Long uid, @NotNull Long teamId) {
        return count(new QueryWrapper<TeamsMembers>().eq("uid", uid).eq("team_id", teamId));
    }

    @Override
    public Integer countByLeaderId(@NotNull Long leaderId, @NotNull Long teamId) {
        return count(new QueryWrapper<TeamsMembers>()
                .eq("uid", leaderId)
                .eq("is_leader", TeamsMemberEnum.LEADER.getType())
                .eq("team_id", teamId));
    }

    @Override
    public User getLeaderByTeamsId(@NotNull Long teamsId) {
        TeamsMembers teamsMembers = getOne(new QueryWrapper<TeamsMembers>()
            .eq("team_id", teamsId)
            .eq("is_leader", TeamsMemberEnum.LEADER.getType()));
        if (ObjectUtils.isEmpty(teamsMembers)) {
            throw new NotFoundException("Leader's uid not found");
        }
        return userMapper.selectById(teamsMembers.getUid());
    }
}
