package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.ut.app.mapper.TagsMapper;
import run.ut.app.mapper.TeamsTagsMapper;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.domain.TeamsTags;
import run.ut.app.service.TeamsTagsService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  TeamsTagsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamsTagsServiceImpl extends ServiceImpl<TeamsTagsMapper, TeamsTags> implements TeamsTagsService {

    private final TagsMapper tagsMapper;

    @Override
    public List<Tags> listByTeamsId(Long teamsId) {
        List<TeamsTags> teamsTags = list(new QueryWrapper<TeamsTags>().eq("team_id", teamsId));
        List<Integer> tagIds = new ArrayList<>();
        for (TeamsTags teamsTag : teamsTags) {
            tagIds.add(teamsTag.getTagId());
        }

        if (tagIds.size() == 0) {
            return new ArrayList<>();
        }
        return tagsMapper.selectBatchIds(tagIds);
    }

    @Override
    public void deleteByTeamsId(Long teamsId) {
        remove(new QueryWrapper<TeamsTags>().eq("team_id", teamsId));
    }
}
