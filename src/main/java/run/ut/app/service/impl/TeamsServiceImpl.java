package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.event.team.TeamsApplyOrInviteEvent;
import run.ut.app.exception.AlreadyExistsException;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.exception.NotFoundException;
import run.ut.app.handler.FileHandlers;
import run.ut.app.mapper.TeamsMapper;
import run.ut.app.mapper.TeamsMembersMapper;
import run.ut.app.mapper.TeamsRecruitmentsMapper;
import run.ut.app.mapper.UserTeamApplyLogMapper;
import run.ut.app.model.domain.*;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsDTO;
import run.ut.app.model.enums.*;
import run.ut.app.model.param.LeaveParam;
import run.ut.app.model.param.TeamApplyOrInviteParam;
import run.ut.app.model.param.TeamsParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.UploadResult;
import run.ut.app.service.TagsService;
import run.ut.app.service.TeamsRecruitmentsTagsService;
import run.ut.app.service.TeamsService;
import run.ut.app.service.TeamsTagsService;
import run.ut.app.utils.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  TeamsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamsServiceImpl extends ServiceImpl<TeamsMapper, Teams> implements TeamsService {

    private final TeamsMapper teamsMapper;
    private final FileHandlers fileHandlers;
    private final TeamsMembersMapper teamsMembersMapper;
    private final TagsService tagsService;
    private final TeamsTagsService teamsTagsService;
    private final TeamsRecruitmentsTagsService teamsRecruitmentsTagsService;
    private final TeamsRecruitmentsMapper teamsRecruitmentsMapper;
    private final UserTeamApplyLogMapper userTeamApplyLogMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TeamsDTO createTeam(TeamsParam teamsParam, Long leaderId) {

        boolean saveTags = false;

        int count = count(new QueryWrapper<Teams>().eq("name", teamsParam.getName()));
        if (count > 0) {
            throw new BadRequestException("团队名已重复！");
        }
        List<Tags> tags = new ArrayList<>();
        if (teamsParam.getTagIds() != null && teamsParam.getTagIds().size() > 0) {
            tags = tagsService.listByIds(teamsParam.getTagIds());
            if (tags.size() < teamsParam.getTagIds().size()) {
                throw new BadRequestException("标签参数有误！");
            }
            saveTags = true;
        }
        boolean hasLogo = !StringUtils.isBlank(teamsParam.getLogo());

        // save team
        Teams team = new Teams()
                .setDescription(teamsParam.getDescription())
                .setName(teamsParam.getName())
                .setStatus(TeamsStatusEnum.getByType(teamsParam.getStatus()));
        if (hasLogo) {
            team.setLogo(teamsParam.getLogo());
        }
        save(team);

        // set leader
        TeamsMembers teamsMember = new TeamsMembers()
                .setUid(leaderId)
                .setIsLeader(TeamsMemberEnum.LEADER)
                .setTeamId(team.getId());
        teamsMembersMapper.insert(teamsMember);

        // save tags
        if (saveTags) {
            // save tags
            List<TeamsTags> teamsTags = tags.stream().map(e -> {
                return new TeamsTags().setTagId(e.getId()).setTeamId(team.getId());
            }).collect(Collectors.toList());
            teamsTagsService.saveBatch(teamsTags);
        }

        return new TeamsDTO().convertFrom(team);
    }

    @Override
    @Transactional
    public List<TagsDTO> saveTeamsTags(String[] tagIds, Long leaderId, Long teamsId) {

        Teams team = getAndCheckTeamByLeaderIdAndTeamId(leaderId, teamsId);

        if (null == tagIds || tagIds.length < 1) {
            teamsTagsService.deleteByTeamsId(teamsId);
            team.setTagIds("").setUpdateTime(null);
            updateById(team);
            return new ArrayList<>();
        }

        // Verify whether the tags exist
        List<Tags> tags = tagsService.listByIds(new HashSet<>(Arrays.asList(tagIds)));

        if (tags.size() != tagIds.length) {
            throw new BadRequestException("传入tagIds有误");
        }

        // Verify that the newly saved tags are the same as the original ones
        List<Tags> tags2 = teamsTagsService.listByTeamsId(teamsId);
        if (tags.equals(tags2)) {
            return BeanUtils.transformFromInBatch(tags, TagsDTO.class);
        }

        // Delete old tags
        teamsTagsService.deleteByTeamsId(teamsId);

        // Repopulate New Tags
        List<TeamsTags> teamsTags = tags.stream().map(e -> {
            return new TeamsTags().setTagId(e.getId()).setTeamId(teamsId);
        }).collect(Collectors.toList());
        teamsTagsService.saveBatch(teamsTags);

        String tagIdsString = StringUtils.join(tagIds, ",");
        team.setUpdateTime(null);
        updateById(team.setTagIds(tagIdsString));

        return BeanUtils.transformFromInBatch(tags, TagsDTO.class);
    }

    @Override
    @Transactional
    @Deprecated
    public List<TagsDTO> saveTeamsRecruitmentsTags(String[] tagIds, Long teamRecruitmentId) {

        TeamsRecruitments recruitment = teamsRecruitmentsMapper.selectById(teamRecruitmentId);

        if (null == tagIds || tagIds.length < 1) {
            teamsRecruitmentsTagsService.deleteByTeamsRecruitmentsId(teamRecruitmentId);
            recruitment.setTagIds("").setUpdateTime(null);
            teamsRecruitmentsMapper.updateById(recruitment);
            return new ArrayList<>();
        }

        // Verify whether the tags exist
        List<Tags> tags = tagsService.listByIds(new HashSet<>(Arrays.asList(tagIds)));
        if (tags.size() != tagIds.length) {
            throw new BadRequestException("传入tagIds有误");
        }

        // Verify that the newly saved tags are the same as the original ones
        List<Tags> tags2 = teamsRecruitmentsTagsService.listByTeamsRecruitmentsId(teamRecruitmentId);
        if (tags.equals(tags2)) {
            return BeanUtils.transformFromInBatch(tags, TagsDTO.class);
        }

        // delete old tags
        teamsRecruitmentsTagsService.deleteByTeamsRecruitmentsId(teamRecruitmentId);

        // Repopulate New Tags
        List<TeamsRecruitmentsTags> recruitmentsTags = tags.stream().map(e -> {
            return new TeamsRecruitmentsTags().setTagId(e.getId()).setTeamRecruitmentId(teamRecruitmentId);
        }).collect(Collectors.toList());
        teamsRecruitmentsTagsService.saveBatch(recruitmentsTags);

        String tagIdsString = StringUtils.join(tagIds, ",");
        recruitment.setTagIds(tagIdsString)
                .setUpdateTime(null);
        teamsRecruitmentsMapper.updateById(recruitment);
        return BeanUtils.transformFromInBatch(tags, TagsDTO.class);
    }

    @Override
    public BaseResponse<String> updateTeamsLogo(MultipartFile logo, Long leaderId, Long teamsId) {

        Teams team = getAndCheckTeamByLeaderIdAndTeamId(leaderId, teamsId);
        if (ObjectUtils.isEmpty(team)) {
            throw new AuthenticationException("只有队长能设置logo");
        }
        UploadResult uploadResult = fileHandlers.upload(logo);
        team.setLogo(uploadResult.getFilePath());
        team.setUpdateTime(null);
        updateById(team);
        return BaseResponse.ok("更新团队头像成功！");
    }

    @Override
    public BaseResponse<String> updateTeamsBaseInfo(TeamsParam teamsParam, Long leaderId) {
        Teams teamByLeaderIdAndTeamId = getTeamByLeaderIdAndTeamId(leaderId, teamsParam.getId());
        if (ObjectUtils.isEmpty(teamByLeaderIdAndTeamId)) {
            throw new AuthenticationException("你不是队长或队伍不存在！");
        }

        String name = teamsParam.getName();
        if (!StringUtils.isBlank(name)) {
            int count = count(new QueryWrapper<Teams>().eq("name", name));
            if (count > 0) {
                throw new AlreadyExistsException("团队名已存在！");
            }
        }

        Teams teams = BeanUtils.transformFrom(teamsParam, Teams.class);
        if (teamsParam.getStatus() != null) {
            TeamsStatusEnum statusEnum = TeamsStatusEnum.getByType(teamsParam.getStatus());
            if (statusEnum != null) {
                teams.setStatus(statusEnum);
            }
        }
        teams.setUpdateTime(null);
        boolean update = updateById(teams);
        return update ? BaseResponse.ok("修改信息成功~") : BaseResponse.ok("请稍后再试~");
    }

    @Override
    public Teams getTeamByLeaderIdAndTeamId(Long leaderId, Long teamsId) {
        TeamsMembers teamsMembers = teamsMembersMapper.selectOne(new QueryWrapper<TeamsMembers>()
                .eq("uid", leaderId)
                .eq("is_leader", TeamsMemberEnum.LEADER)
                .eq("team_id", teamsId));
        if (ObjectUtils.isEmpty(teamsMembers)) {
            return null;
        }
        return getById(teamsMembers.getTeamId());
    }

    @Override
    public BaseResponse<String> userApplyToTeam(TeamApplyOrInviteParam teamApplyParam) {

        UserTeamApplyLog userTeamApplyLog = teamApplyParam.convertTo();
        userTeamApplyLog.setId(null);

        Long uid = userTeamApplyLog.getUid();
        Long teamId = userTeamApplyLog.getTeamId();
        Long recruitmentId = userTeamApplyLog.getRecruitmentId();

        // Verify that the user is already in the team
        Integer count1 = teamsMembersMapper.selectCount(new QueryWrapper<TeamsMembers>()
                .eq("uid", uid).eq("team_id", teamId));
        if (count1 > 0) {
            throw new AlreadyExistsException("您已经是该团队的成员~");
        }

        Integer count = userTeamApplyLogMapper.selectCount(new QueryWrapper<UserTeamApplyLog>()
                .eq("uid", uid).eq("team_id", teamId)
                .eq("status", ApplyStatusEnum.WAITING.getType())
                .eq("mode", ApplyModeEnum.USER_TO_TEAM.getType()));
        if (count > 0) {
            throw new AlreadyExistsException("你已申请过该团队！不过仍处于审核阶段~, 还请耐心等候");
        }

        Teams teams = this.baseMapper.selectById(teamId);
        if (ObjectUtils.isEmpty(teams)) {
            throw new NotFoundException("申请加入的团队不存在！");
        }

        if (recruitmentId != 0) {
            Integer count2 = teamsRecruitmentsMapper.selectCount(new QueryWrapper<TeamsRecruitments>()
                    .eq("team_id", teamId).eq("id", recruitmentId));
            if (count2 < 1) {
                throw new NotFoundException("申请的职位不存在！");
            }
        }

        userTeamApplyLog.setMode(ApplyModeEnum.USER_TO_TEAM)
                .setStatus(ApplyStatusEnum.WAITING);


        // Publish event
        eventPublisher.publishEvent(new TeamsApplyOrInviteEvent(this, teamApplyParam, ApplyModeEnum.USER_TO_TEAM));

        userTeamApplyLogMapper.insert(userTeamApplyLog);

        return BaseResponse.ok("申请成功！请耐心等待审核");
    }

    @Override
    public BaseResponse<String> teamInvitesUser(TeamApplyOrInviteParam teamInviteParam) {
        UserTeamApplyLog userTeamApplyLog = teamInviteParam.convertTo();
        userTeamApplyLog.setId(null);

        Long uid = userTeamApplyLog.getUid();
        Long teamId = userTeamApplyLog.getTeamId();
        Long recruitmentId = userTeamApplyLog.getRecruitmentId();

        // Verify that the user is already in the team
        Integer count1 = teamsMembersMapper.selectCount(new QueryWrapper<TeamsMembers>()
                .eq("uid", uid).eq("team_id", teamId));
        if (count1 > 0) {
            throw new AlreadyExistsException("邀请者已是该团队的成员~");
        }

        Integer count = userTeamApplyLogMapper.selectCount(new QueryWrapper<UserTeamApplyLog>()
                .eq("uid", uid).eq("team_id", teamId)
                .eq("status", ApplyStatusEnum.WAITING.getType())
                .eq("mode", ApplyModeEnum.TEAM_TO_USER.getType()));
        if (count > 0) {
            throw new AlreadyExistsException("你已邀请过该用户！不过对方还在考虑~, 还请耐心等候");
        }

        if (recruitmentId != 0) {
            Integer count2 = teamsRecruitmentsMapper.selectCount(new QueryWrapper<TeamsRecruitments>()
                    .eq("team_id", teamId).eq("id", recruitmentId));
            if (count2 < 1) {
                throw new NotFoundException("邀请的职位不存在！");
            }
        }

        userTeamApplyLog.setMode(ApplyModeEnum.TEAM_TO_USER)
                .setStatus(ApplyStatusEnum.WAITING);
        userTeamApplyLogMapper.insert(userTeamApplyLog);

        // publish event
        eventPublisher.publishEvent(new TeamsApplyOrInviteEvent(this, teamInviteParam, ApplyModeEnum.TEAM_TO_USER));

        return BaseResponse.ok("邀请成功！请耐心等待回应~");
    }

    @Override
    public List<TeamsDTO> listTeamsByUid(Long uid) {
        return teamsMapper.listTeamsByUid(uid);
    }

    @Override
    public BaseResponse<String> leave(LeaveParam leaveParam) {
        Long uid = leaveParam.getUid();
        Long teamsId = leaveParam.getTeamsId();
        Long firedUid = leaveParam.getFiredUid();

        if (leaveParam.getMode() == LeaveModeEnum.EXPEL_USER.getType()) {
            getAndCheckTeamByLeaderIdAndTeamId(uid, teamsId);
            TeamsMembers teamsMembers = teamsMembersMapper.selectOne(new QueryWrapper<TeamsMembers>()
                .eq("uid", firedUid)
                .eq("team_id", teamsId)
                .eq("is_leader", TeamsMemberEnum.NORMAL.getType()));
            if (ObjectUtils.isEmpty(teamsMembers)) {
                throw new NotFoundException("队伍中没有该成员");
            }
            teamsMembersMapper.deleteById(teamsMembers.getId());
        } else {
            TeamsMembers teamsMembers = teamsMembersMapper.selectOne(new QueryWrapper<TeamsMembers>()
                .eq("uid", uid)
                .eq("team_id", teamsId));
            if (ObjectUtils.isEmpty(teamsMembers)) {
                throw new NotFoundException("队伍中没有该成员");
            }
            teamsMembersMapper.deleteById(teamsMembers.getId());
            if (teamsMembers.getIsLeader() == TeamsMemberEnum.LEADER) {
                // 如果主动退出者是队长，则退出后取一个队伍成员为队长，如果没有成员了则解散队伍
                TeamsMembers teamsMembers1 = teamsMembersMapper.selectOne(new QueryWrapper<TeamsMembers>().eq("team_id", teamsId));
                if (ObjectUtils.isEmpty(teamsMembers1)) {
                    removeById(teamsId);
                } else {
                    teamsMembers1.setIsLeader(TeamsMemberEnum.LEADER);
                    teamsMembersMapper.updateById(teamsMembers1);
                }
            }
        }
        return BaseResponse.ok("操作成功");
    }

    @Override
    public Teams getAndCheckTeamByLeaderIdAndTeamId(Long leaderId, Long teamsId) {
        Teams team = getTeamByLeaderIdAndTeamId(leaderId, teamsId);
        if (ObjectUtils.isEmpty(team)) {
            throw new NotFoundException("团队不存在！");
        }
        return team;
    }

    @Override
    public List<Long> getTeamIdsByLeaderId(Long leaderId) {
        List<TeamsMembers> teamsMembers = teamsMembersMapper.selectList(new QueryWrapper<TeamsMembers>()
                .select("team_id")
                .eq("uid", leaderId)
                .eq("is_leader", TeamsMemberEnum.LEADER.getType()));
        List<Long> teamIds = new ArrayList<>();
        for (TeamsMembers teamsMember : teamsMembers) {
            teamIds.add(teamsMember.getTeamId());
        }
        return teamIds;
    }

    @Override
    public List<TeamsDTO> listTeamsByLeaderId(Long leaderId) {
        List<Long> teamIdsByLeaderId = getTeamIdsByLeaderId(leaderId);
        if (ObjectUtils.isEmpty(teamIdsByLeaderId) || teamIdsByLeaderId.size() == 0) {
            return new ArrayList<>();
        }
        return BeanUtils.transformFromInBatch(listByIds(teamIdsByLeaderId), TeamsDTO.class);
    }

    @Override
    public TeamsMembers getMemberByUidAndTeamId(Long uid, Long teamId) {
        return teamsMembersMapper.selectOne(new QueryWrapper<TeamsMembers>().eq("uid", uid).eq("team_id", teamId));
    }

    @Override
    @Transactional
    public BaseResponse<String> transferLeader(Long leaderId, Long targetUid, Long teamId) {
        TeamsMembers member = getMemberByUidAndTeamId(targetUid, teamId);
        if (ObjectUtils.isEmpty(member)) {
            throw new NotFoundException("队伍中不存在该成员");
        }
        TeamsMembers leader = getMemberByUidAndTeamId(leaderId, teamId);
        if (ObjectUtils.isEmpty(leader) || leader.getIsLeader() != TeamsMemberEnum.LEADER) {
            throw new AuthenticationException("当前用户无法操作");
        }
        leader.setIsLeader(TeamsMemberEnum.NORMAL);
        member.setIsLeader(TeamsMemberEnum.LEADER);
        teamsMembersMapper.updateById(member);
        teamsMembersMapper.updateById(leader);
        return BaseResponse.ok("操作成功~");
    }

    @Override
    @Transactional
    public BaseResponse<String> disband(Long leaderId, Long teamId) {
        Teams team = getTeamByLeaderIdAndTeamId(leaderId, teamId);
        if (ObjectUtils.isEmpty(team)) {
            throw new AuthenticationException("无权操作！");
        }
        removeById(teamId);
        teamsMembersMapper.delete(new QueryWrapper<TeamsMembers>().eq("team_id", teamId));
        return BaseResponse.ok("解散队伍成功");
    }

}
