package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.ut.app.mapper.TagsMapper;
import run.ut.app.mapper.UserTagsMapper;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.domain.UserTags;
import run.ut.app.service.UserTagsService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  UserTagsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserTagsServiceImpl extends ServiceImpl<UserTagsMapper, UserTags> implements UserTagsService {

    private final TagsMapper tagsMapper;

    @Override
    public List<Tags> listByUid(Long uid) {
        List<UserTags> userTags = list(new QueryWrapper<UserTags>().eq("uid", uid));
        List<Long> tagIds = new ArrayList<>();
        for (UserTags userTag : userTags) {
            tagIds.add(userTag.getTagId());
        }

        if (tagIds.size() == 0) {
            return new ArrayList<>();
        }
        return tagsMapper.selectBatchIds(tagIds);
    }

    @Override
    public void deleteByUid(Long uid) {
        remove(new QueryWrapper<UserTags>().eq("uid", uid));
    }
}
