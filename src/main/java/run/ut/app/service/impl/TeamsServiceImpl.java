package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.exception.NotFoundException;
import run.ut.app.handler.FileHandlers;
import run.ut.app.mapper.TeamsMembersMapper;
import run.ut.app.model.domain.*;
import run.ut.app.mapper.TeamsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsDTO;
import run.ut.app.model.enums.TeamsMemberEnum;
import run.ut.app.model.enums.TeamsStatusEnum;
import run.ut.app.model.enums.UserInfoStatusEnum;
import run.ut.app.model.param.TeamsParam;
import run.ut.app.model.support.UploadResult;
import run.ut.app.service.TagsService;
import run.ut.app.service.TeamsMembersService;
import run.ut.app.service.TeamsService;
import run.ut.app.service.TeamsTagsService;
import run.ut.app.utils.ImageUtils;

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

    @Override
    @Transactional
    public TeamsDTO createTeam(TeamsParam teamsParam, Long leaderId, MultipartFile logo) throws HttpMediaTypeNotAcceptableException {

        int count = count(new QueryWrapper<Teams>().eq("name", teamsParam.getName()));
        if (count > 0){
            throw new BadRequestException("团队名已重复！");
        }

        // upload logo
        UploadResult uploadResult = null;
        boolean hasLogo = !ObjectUtils.isEmpty(logo);
        if (hasLogo){
            if (!ImageUtils.isImage()){
                throw new HttpMediaTypeNotAcceptableException("只接受图片格式文件！");
            }
            uploadResult = fileHandlers.upload(logo);
        }

        // save team
        Teams team = new Teams()
                .setDescription(teamsParam.getDescription())
                .setName(teamsParam.getName())
                .setStatus(teamsParam.getStatus());
        if (hasLogo){
            team.setLogo(uploadResult.getFilePath());
        }
        save(team);

        // set leader
        TeamsMembers teamsMember = new TeamsMembers()
                .setUid(leaderId)
                .setIsLeader(TeamsMemberEnum.LEADER.getType())
                .setTeamId(team.getId());
        teamsMembersMapper.insert(teamsMember);
        return new TeamsDTO().convertFrom(team);
    }

    @Override
    @Transactional
    public List<TagsDTO> saveTeamsTags(String[] tagIds, Long leaderId) {
        // Verify whether the tags exist
        List<Tags> tags = tagsService.listByIds(new HashSet<>(Arrays.asList(tagIds)));

        if (tags.size() != tagIds.length){
            throw new BadRequestException("传入tagIds有误");
        }

        Teams team = getTeamByLeaderId(leaderId);
        Long teamsId = team.getId();
        if (ObjectUtils.isEmpty(team)){
            throw new NotFoundException("团队不存在！");
        }

        // Verify that the newly saved tags are the same as the original ones
        List<Tags> tags2 = teamsTagsService.listByTeamsId(teamsId);
        if (tags.equals(tags2)){
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
    public Teams getTeamByLeaderId(Long leaderId) {
        TeamsMembers teamsMembers = teamsMembersMapper.selectOne(new QueryWrapper<TeamsMembers>()
                .eq("uid", leaderId).eq("is_leader", TeamsMemberEnum.LEADER));
        if (ObjectUtils.isEmpty(teamsMembers)){
            return null;
        }
        return getById(teamsMembers.getTeamId());
    }
}
