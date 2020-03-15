package run.ut.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.domain.User;
import run.ut.app.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.domain.UserTags;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.enums.UserInfoStatusEnum;
import run.ut.app.service.TagsService;
import run.ut.app.service.UserInfoService;
import run.ut.app.service.UserService;
import run.ut.app.service.UserTagsService;

import java.util.ArrayList;
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
    private final UserInfoService userInfoService;


    @Override
    @Transactional
    public List<TagsDTO> saveUserTags(Long uid, String[] tagIds) throws Exception {

        UserInfo userInfo = userInfoService.getOneActivatedByUid(uid);

        if (null == tagIds || tagIds.length < 1){
            userTagsService.deleteByUid(uid);
            userInfo.setTagIds("").setUpdateTime(null);
            userInfoService.updateById(userInfo);
            return new ArrayList<>();
        }


        if (ObjectUtils.isEmpty(userInfo) || (userInfo.getStatus() != UserInfoStatusEnum.PASS)){
            throw new AuthenticationException("只有经过认证的用户才能设置标签！");
        }

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

        String tagIdsString = StringUtils.join(tagIds, ",");
        userInfo.setUpdateTime(null);
        userInfoService.updateById(userInfo.setTagIds(tagIdsString));

        return tags.stream().map(e -> {
            return (TagsDTO)new TagsDTO().convertFrom(e);
        }).collect(Collectors.toList());
    }

}
