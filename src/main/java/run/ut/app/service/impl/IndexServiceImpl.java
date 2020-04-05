package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.TeamsMapper;
import run.ut.app.mapper.TeamsMembersMapper;
import run.ut.app.mapper.TeamsRecruitmentsMapper;
import run.ut.app.mapper.UserMapper;
import run.ut.app.model.domain.Teams;
import run.ut.app.model.domain.TeamsRecruitments;
import run.ut.app.model.domain.User;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsRecruitmentsDTO;
import run.ut.app.model.dto.UserExperiencesDTO;
import run.ut.app.model.enums.TeamsStatusEnum;
import run.ut.app.model.param.SearchRecruitmentParam;
import run.ut.app.model.param.SearchStudentParam;
import run.ut.app.model.param.SearchTeamParam;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.StudentVO;
import run.ut.app.model.vo.TeamMemberVO;
import run.ut.app.model.vo.TeamVO;
import run.ut.app.model.vo.TeamsRecruitmentsVO;
import run.ut.app.service.*;
import run.ut.app.utils.BeanUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class IndexServiceImpl implements IndexService {

    private final UserMapper userMapper;
    private final TagsService tagsService;
    private final UserTagsService userTagsService;
    private final UserExperiencesService userExperiencesService;
    private final DataSchoolService dataSchoolService;
    private final UserInfoService userInfoService;
    private final TeamsMapper teamsMapper;
    private final TeamsTagsService teamsTagsService;
    private final TeamsMembersMapper teamsMembersMapper;
    private final TeamsRecruitmentsService teamsRecruitmentsService;
    private final TeamsRecruitmentsMapper teamsRecruitmentsMapper;
    private final TeamsRecruitmentsTagsService teamsRecruitmentsTagsService;

    @Override
    public CommentPage<StudentVO> listStudentByParam(SearchStudentParam searchStudentParam, Page page) {

        IPage<StudentVO> studentVOIPage = userMapper.listStudentByParam(page, searchStudentParam);
        List<StudentVO> records = studentVOIPage.getRecords();
        for (int i = 0; i < records.size(); i++) {
            StudentVO studentVO = records.get(i);
            studentVO.setSchool(dataSchoolService.getById(studentVO.getSchoolId()).getName());
            String tagIds = studentVO.getTagIds();
            if (!StringUtils.isBlank(tagIds)) {
                List<TagsDTO> tagsDTOList = BeanUtils
                    .transformFromInBatch(tagsService.listByIds(Arrays.asList(tagIds.split(","))), TagsDTO.class);
                studentVO.setTags(tagsDTOList);
            }
        }

        long total = studentVOIPage.getTotal();

        return new CommentPage<>(total, records);
    }

    @Override
    public CommentPage<TeamVO> listTeamByParam(SearchTeamParam searchTeamParam, Page page) {
        if (StringUtils.isAllBlank(searchTeamParam.getName(), searchTeamParam.getTagId() + "")) {
            searchTeamParam.setStatus(TeamsStatusEnum.PUBLIC.getType());
        }
        IPage<TeamVO> teamsPage = teamsMapper.listTeamByParam(page, searchTeamParam);
        List<TeamVO> teamVOList = teamsPage.getRecords();
        for (int i = 0; i < teamVOList.size(); i++) {
            TeamVO teamVO = teamVOList.get(i);
            String tagIds = teamVO.getTagIds();
            if (!StringUtils.isBlank(tagIds)) {
                List<TagsDTO> tagsDTOList = BeanUtils
                    .transformFromInBatch(tagsService.listByIds(Arrays.asList(tagIds.split(","))), TagsDTO.class);
                teamVO.setTags(tagsDTOList);
            }
        }
        return new CommentPage<>(teamsPage.getTotal(), teamVOList);
    }

    @Override
    public CommentPage<TeamsRecruitmentsVO> listRecruitmentByParam(SearchRecruitmentParam searchRecruitmentParam, Page page) {
        IPage<TeamsRecruitmentsVO> teamsRecruitmentsVOIPage = teamsRecruitmentsMapper.listRecruitmentByParam(page, searchRecruitmentParam);
        long total = teamsRecruitmentsVOIPage.getTotal();

        List<TeamsRecruitmentsVO> records = teamsRecruitmentsVOIPage.getRecords();
//        for (int i = 0; i < records.size(); i++){
//            TeamsRecruitmentsVO teamsRecruitmentsVO = records.get(i);
//            String tagIds = teamsRecruitmentsVO.getTagIds();
//            if (!StringUtils.isBlank(tagIds)){
//                List<TagsDTO> tagsDTOList = tagsService.listByIds(Arrays.asList(tagIds.split(","))).stream().map(e -> {
//                    return (TagsDTO) new TagsDTO().convertFrom(e);
//                }).collect(Collectors.toList());
//                teamsRecruitmentsVO.setTags(tagsDTOList);
//            }
//        }
        return new CommentPage<>(total, records);
    }

    @Override
    public StudentVO showUserPageInfo(Long uid) {
        User user = userMapper.selectById(uid);
        if (ObjectUtils.isEmpty(user)) {
            throw new NotFoundException("该id的用户不存在！");
        }
        UserInfo userInfo = userInfoService.getOneActivatedByUid(uid);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new NotFoundException("该用户没有认证信息，uid: " + uid);
        }
        List<TagsDTO> tagsDTOList = BeanUtils.transformFromInBatch(userTagsService.listByUid(uid), TagsDTO.class);
        List<UserExperiencesDTO> userExperiencesDTOList = BeanUtils.
            transformFromInBatch(userExperiencesService.getUserExperiencesByUid(uid), UserExperiencesDTO.class);
        String schoolName = dataSchoolService.getById(userInfo.getSchoolId()).getName();

        return new StudentVO()
                .setTags(tagsDTOList)
                .setExperiences(userExperiencesDTOList)
                .setSchool(schoolName)
                .setAvatar(user.getAvatar())
                .setDegree(userInfo.getDegreeId())
                .setGrade(userInfo.getGrade())
                .setSubject(userInfo.getSubject())
                .setCredentialsPhotoFront(userInfo.getCredentialsPhotoFront())
                .setCredentialsPhotoReverse(userInfo.getCredentialsPhotoReverse())
                .setRealName(userInfo.getRealName())
                .setDescription(user.getDescription())
                .setUid(user.getUid())
                .setNickname(user.getNickname())
                .setSex(user.getSex())
                .setRoles(user.getRoles());
    }

    @Override
    public TeamVO showTeamsInfo(Long teamsId) {
        TeamVO teamVO = new TeamVO();

        // Basic info of the team
        Teams team = teamsMapper.selectById(teamsId);
        if (ObjectUtils.isEmpty(team)) {
            throw new NotFoundException("该id的团队不存在！");
        }
        teamVO = BeanUtils.transformFrom(team, TeamVO.class);

        // Get tags
        List<TagsDTO> tags = BeanUtils.transformFromInBatch(teamsTagsService.listByTeamsId(teamsId), TagsDTO.class);
        teamVO.setTags(tags);

        // Get members
        List<TeamMemberVO> memberVOs = teamsMembersMapper.listSimpleMemberInfoByTeamsId(teamsId);
        teamVO.setMembers(memberVOs);

        // Get recruitments
        List<TeamsRecruitmentsDTO> teamsRecruitmentsDTOList = BeanUtils.
            transformFromInBatch(teamsRecruitmentsService.listTeamsRecruitmentsByTeamsId(teamsId), TeamsRecruitmentsDTO.class);
        teamVO.setRecruitments(teamsRecruitmentsDTOList);

        return teamVO;
    }

    @Override
    public TeamsRecruitmentsVO showRecruitmentsInfo(Long recruitmentsId) {
        TeamsRecruitments recruitment = teamsRecruitmentsService.getById(recruitmentsId);
        if (ObjectUtils.isEmpty(recruitment)) {
            throw new NotFoundException("该id的职位不存在！");
        }

        TeamsRecruitmentsVO teamsRecruitmentsVO = new TeamsRecruitmentsVO().convertFrom(recruitment);

//        // Get tags
//        List<TagsDTO> tags = teamsRecruitmentsTagsService.listByTeamsRecruitmentsId(recruitmentsId)
//                .stream().map(e -> (TagsDTO) new TagsDTO().convertFrom(e)).collect(Collectors.toList());
//        teamsRecruitmentsVO.setTags(tags);

        // Get info about the team
        Teams teams = teamsMapper.selectById(recruitment.getTeamId());
        teamsRecruitmentsVO.setTeamVO(new TeamVO().convertFrom(teams));

        return teamsRecruitmentsVO;
    }

    @Override
    public List<TeamsRecruitmentsDTO> listRecruitmentsByTeamId(@Nonnull Long teamId) {
        return BeanUtils.transformFromInBatch(
                teamsRecruitmentsMapper.selectList(new QueryWrapper<TeamsRecruitments>().eq("team_id", teamId)),
                TeamsRecruitmentsDTO.class
            );
    }

}
