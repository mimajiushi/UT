package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.ut.app.mapper.UserMapper;
import run.ut.app.model.domain.User;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.UserExperiencesDTO;
import run.ut.app.model.param.SearchStudentParam;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.StudentVO;
import run.ut.app.service.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public CommentPage<StudentVO> listStudentByParam(SearchStudentParam searchStudentParam, Page page) {

        IPage<StudentVO> studentVOIPage = userMapper.listStudentByParam(page, searchStudentParam.getGrade(), searchStudentParam.getTagId(),
                searchStudentParam.getSchoolId(), searchStudentParam.getDegreeId());
        List<StudentVO> records = studentVOIPage.getRecords();
        for (int i = 0; i < records.size(); i++){
            StudentVO studentVO = records.get(i);
            studentVO.setSchool(dataSchoolService.getById(studentVO.getSchoolId()).getName());
            String tagIds = studentVO.getTagIds();
            if (!StringUtils.isBlank(tagIds)){
                List<TagsDTO> tagsDTOList = tagsService.listByIds(Arrays.asList(tagIds.split(","))).stream().map(e -> {
                    return (TagsDTO) new TagsDTO().convertFrom(e);
                }).collect(Collectors.toList());
                studentVO.setTags(tagsDTOList);
            }
        }

        long total = studentVOIPage.getTotal();

        return new CommentPage<>(total, records);
    }

    @Override
    public StudentVO showStudentPage(Long uid) {
        User user = userMapper.selectById(uid);
        UserInfo userInfo = userInfoService.getOneByUid(uid);
        List<TagsDTO> tagsDTOList = userTagsService.listByUid(uid)
                .stream().map(e -> (TagsDTO) new TagsDTO().convertFrom(e)).collect(Collectors.toList());
        List<UserExperiencesDTO> userExperiencesDTOList = userExperiencesService.getUserExperiencesByUid(uid)
                .stream().map(e -> (UserExperiencesDTO) new UserExperiencesDTO().convertFrom(e)).collect(Collectors.toList());
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
                .setNickname(user.getNickname());
    }


}
