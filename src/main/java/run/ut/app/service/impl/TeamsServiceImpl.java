package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.exception.*;
import run.ut.app.handler.FileHandlers;
import run.ut.app.mapper.TeamsMembersMapper;
import run.ut.app.mapper.TeamsRecruitmentsMapper;
import run.ut.app.mapper.UserTeamApplyLogMapper;
import run.ut.app.model.domain.*;
import run.ut.app.mapper.TeamsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsDTO;
import run.ut.app.model.enums.ApplyModeEnum;
import run.ut.app.model.enums.ApplyStatusEnum;
import run.ut.app.model.enums.TeamsMemberEnum;
import run.ut.app.model.enums.TeamsStatusEnum;
import run.ut.app.model.param.TeamApplyOrInviteParam;
import run.ut.app.model.param.TeamsParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.UploadResult;
import run.ut.app.service.TagsService;
import run.ut.app.service.TeamsRecruitmentsTagsService;
import run.ut.app.service.TeamsService;
import run.ut.app.service.TeamsTagsService;
import run.ut.app.utils.ImageUtils;

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

    private final FileHandlers fileHandlers;
    private final TeamsMembersMapper teamsMembersMapper;
    private final TagsService tagsService;
    private final TeamsTagsService teamsTagsService;
    private final TeamsRecruitmentsTagsService teamsRecruitmentsTagsService;
    private final TeamsRecruitmentsMapper teamsRecruitmentsMapper;
    private final UserTeamApplyLogMapper userTeamApplyLogMapper;

    @Override
    @Transactional
    public TeamsDTO createTeam(TeamsParam teamsParam, Long leaderId, MultipartFile logo) {

        int count = count(new QueryWrapper<Teams>().eq("name", teamsParam.getName()));
        if (count > 0) {
            throw new BadRequestException("团队名已重复！");
        }

        // upload logo
        UploadResult uploadResult = null;
        boolean hasLogo = !ObjectUtils.isEmpty(logo);
        if (hasLogo) {
            if (!ImageUtils.isImage()) {
                throw new FileOperationException("只接受图片格式文件！");
            }
            uploadResult = fileHandlers.upload(logo);
        }

        // save team
        Teams team = new Teams()
                .setDescription(teamsParam.getDescription())
                .setName(teamsParam.getName())
                .setStatus(TeamsStatusEnum.getByType(teamsParam.getStatus()));
        if (hasLogo) {
            team.setLogo(uploadResult.getFilePath());
        }
        save(team);

        // set leader
        TeamsMembers teamsMember = new TeamsMembers()
                .setUid(leaderId)
                .setIsLeader(TeamsMemberEnum.LEADER)
                .setTeamId(team.getId());
        teamsMembersMapper.insert(teamsMember);
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
            return tags.stream().map(e -> {
                return (TagsDTO)new TagsDTO().convertFrom(e);
            }).collect(Collectors.toList());
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

        return tags.stream().map(e -> {
            return (TagsDTO)new TagsDTO().convertFrom(e);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
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
            return tags.stream().map(e -> {
                return (TagsDTO)new TagsDTO().convertFrom(e);
            }).collect(Collectors.toList());
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
        return tags.stream().map(e -> {
            return (TagsDTO)new TagsDTO().convertFrom(e);
        }).collect(Collectors.toList());
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


        // TODO send resume email

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

        return BaseResponse.ok("邀请成功！请耐心等待回应~");
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
        return listByIds(teamIdsByLeaderId).stream().map(e -> (TeamsDTO)new TeamsDTO().convertFrom(e)).collect(Collectors.toList());
    }
}
