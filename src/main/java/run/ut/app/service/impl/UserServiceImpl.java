package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.ut.app.exception.BadRequestException;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.domain.User;
import run.ut.app.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.model.domain.UserTags;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.service.TagsService;
import run.ut.app.service.UserService;
import run.ut.app.service.UserTagsService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  UserServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-09
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final TagsService tagsService;
    private final UserTagsService userTagsService;


    @Override
    @Transactional
    public List<TagsDTO> saveUserTags(Long uid, String[] tagIds) throws Exception {
        // Verify whether the tags exist
        List<Tags> tags = tagsService.listByIds(new HashSet<>(Arrays.asList(tagIds)));

        if (tags.size() != tagIds.length){
            throw new BadRequestException("传入tagIds有误");
        }

        // Verify that the newly saved tags are the same as the original ones
        List<Tags> tags1 = userTagsService.listByUid(uid);
        if (tags.equals(tags1)){
            return tags.stream().map(e -> {
                return (TagsDTO)new TagsDTO().convertFrom(e);
            }).collect(Collectors.toList());
        }

        // Delete old tags
        userTagsService.deleteByUid(uid);

        // Repopulate New Tags
        List<UserTags> userTags = tags.stream().map(e -> {
            return (UserTags) new UserTags().setTagId(e.getId()).setUid(uid);
        }).collect(Collectors.toList());
        userTagsService.saveBatch(userTags);

        return tags.stream().map(e -> {
            return (TagsDTO)new TagsDTO().convertFrom(e);
        }).collect(Collectors.toList());
    }

}
