package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.ut.app.mapper.TagsMapper;
import run.ut.app.mapper.TeamsRecruitmentsTagsMapper;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.domain.TeamsRecruitmentsTags;
import run.ut.app.service.TeamsRecruitmentsTagsService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  TeamsRecruitmentsTagsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-15
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Deprecated
@DubboService
public class TeamsRecruitmentsTagsServiceImpl extends ServiceImpl<TeamsRecruitmentsTagsMapper, TeamsRecruitmentsTags> implements TeamsRecruitmentsTagsService {

    private final TagsMapper tagsMapper;

    @Override
    public List<Tags> listByTeamsRecruitmentsId(Long teamRecruitmentId) {
        List<TeamsRecruitmentsTags> teamsRecruitmentsTags = list(new QueryWrapper<TeamsRecruitmentsTags>().eq("team_recruitment_id", teamRecruitmentId));
        List<Long> tagIds = new ArrayList<>();
        for (TeamsRecruitmentsTags recruitmentsTags : teamsRecruitmentsTags) {
            tagIds.add(recruitmentsTags.getTagId());
        }
        if (tagIds.size() == 0) {
            return new ArrayList<>();
        }
        return tagsMapper.selectBatchIds(tagIds);
    }

    @Override
    public void deleteByTeamsRecruitmentsId(Long teamRecruitmentId) {
        remove(new QueryWrapper<TeamsRecruitmentsTags>().eq("team_recruitment_id", teamRecruitmentId));
    }
}
