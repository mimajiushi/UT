package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.ut.app.config.redis.RedisKey;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.exception.NotFoundException;
import run.ut.app.exception.WeChatException;
import run.ut.app.handler.FileHandlers;
import run.ut.app.mail.MailService;
import run.ut.app.mapper.UserMapper;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.domain.User;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.domain.UserTags;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.dto.UserExperiencesDTO;
import run.ut.app.model.enums.SexEnum;
import run.ut.app.model.enums.UserInfoStatusEnum;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.param.WeChatLoginParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.WeChatResponse;
import run.ut.app.model.vo.StudentVO;
import run.ut.app.security.token.AuthToken;
import run.ut.app.security.util.JwtOperator;
import run.ut.app.service.*;
import run.ut.app.utils.BeanUtils;
import run.ut.app.utils.RandomUtils;

import java.io.IOException;
import java.util.*;
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
@DubboService
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final TagsService tagsService;
    private final UserTagsService userTagsService;
    private final UserInfoService userInfoService;
    private final JwtOperator jwtOperator;
    private final UserExperiencesService userExperiencesService;
    private final DataSchoolService dataSchoolService;
    private final FileHandlers fileHandlers;
    private final RedisService redisService;
    private final MailService mailService;


    @Override
    @Transactional
    public List<TagsDTO> saveUserTags(Long uid, String[] tagIds) throws Exception {

        UserInfo userInfo = userInfoService.getOneActivatedByUid(uid);

        if (null == tagIds || tagIds.length < 1) {
            userTagsService.deleteByUid(uid);
            userInfo.setTagIds("").setUpdateTime(null);
            userInfoService.updateById(userInfo);
            return new ArrayList<>();
        }


        if (ObjectUtils.isEmpty(userInfo) || (userInfo.getStatus() != UserInfoStatusEnum.PASS)) {
            throw new AuthenticationException("只有经过认证的用户才能设置标签！");
        }

        // Verify whether the tags exist
        List<Tags> tags = tagsService.listByIds(new HashSet<>(Arrays.asList(tagIds)));

        if (tags.size() != tagIds.length) {
            throw new BadRequestException("传入tagIds有误");
        }

        // Verify that the newly saved tags are the same as the original ones
        List<Tags> tags1 = userTagsService.listByUid(uid);
        if (tags.equals(tags1)) {
            return BeanUtils.transformFromInBatch(tags, TagsDTO.class);
        }

        // Delete old tags
        userTagsService.deleteByUid(uid);

        // Repopulate New Tags
        List<UserTags> userTags = tags.stream().map(e -> new UserTags().setTagId(e.getId()).setUid(uid))
            .collect(Collectors.toList());

        userTagsService.saveBatch(userTags);

        String tagIdsString = StringUtils.join(tagIds, ",");
        userInfo.setUpdateTime(null);
        userInfoService.updateById(userInfo.setTagIds(tagIdsString));

        return BeanUtils.transformFromInBatch(tags, TagsDTO.class);
    }

    @Override
    public StudentVO showSelfPage(Long uid) {
        User user = getById(uid);
        if (ObjectUtils.isEmpty(user)) {
            throw new NotFoundException("该id的用户不存在！");
        }
        UserInfo userInfo = userInfoService.getOneActivatedByUid(uid);
        List<TagsDTO> tagsDTOList = BeanUtils.transformFromInBatch(userTagsService.listByUid(uid), TagsDTO.class);
        List<UserExperiencesDTO> userExperiencesDTOList = BeanUtils.transformFromInBatch(userExperiencesService.getUserExperiencesByUid(uid), UserExperiencesDTO.class);

        boolean hasAuthInfo = !ObjectUtils.isEmpty(userInfo);

        StudentVO studentVO = new StudentVO()
                .setTags(tagsDTOList)
                .setExperiences(userExperiencesDTOList)
                .setAvatar(user.getAvatar())
                .setDescription(user.getDescription())
                .setUid(user.getUid())
                .setNickname(user.getNickname())
                .setPhoneNumber(user.getPhoneNumber())
                .setEmail(user.getEmail())
                .setSex(user.getSex())
                .setHasAuthInfo(hasAuthInfo)
                .setRoles(user.getRoles());
        if (hasAuthInfo) {
            String schoolName = dataSchoolService.getById(userInfo.getSchoolId()).getName();
            studentVO.setDegree(userInfo.getDegreeId())
                    .setGrade(userInfo.getGrade())
                    .setSubject(userInfo.getSubject())
                    .setCredentialsPhotoFront(userInfo.getCredentialsPhotoFront())
                    .setCredentialsPhotoReverse(userInfo.getCredentialsPhotoReverse())
                    .setRealName(userInfo.getRealName())
                    .setSchool(schoolName);
        }
        return studentVO;
    }

    @Override
    public UserDTO wechatLogin(WeChatLoginParam weChatLoginParam, WeChatResponse weChatResponse) {
        if (null != weChatResponse.getErrcode()) {
            throw new WeChatException(weChatResponse.getErrmsg());
        }
        String avatar = weChatLoginParam.getAvatarUrl();
        String nickname = weChatLoginParam.getNickName();
        String openid = weChatResponse.getOpenid();
        User user = getUserByOpenId(openid);
        if (ObjectUtils.isEmpty(user)) {
            // register and login
            user = new User().setRoles(UserRolesEnum.ROLE_TOURIST.getType())
                    .setOpenid(openid)
                    .setAvatar(avatar)
                    .setNickname(nickname)
                    .setSex(SexEnum.UNKNOW);
            save(user);
            AuthToken authToken = jwtOperator.buildAuthToken(user);
            return new UserDTO().convertFrom(user).setToken(authToken);
        }

        // login
        AuthToken authToken = jwtOperator.buildAuthToken(user);
        return new UserDTO().convertFrom(user).setToken(authToken);
    }

    @Override
    public User getUserByOpenId(String openId) {
        return getOne(new QueryWrapper<User>().eq("openid", openId));
    }

    @Override
    public User getUserByEmail(String email) {
        return getOne(new QueryWrapper<User>().eq("email", email));
    }

    @Override
    public BaseResponse<String> updateUserAvatar(Long uid, String avatarPaht) {
        User user = getById(uid);
        user.setAvatar(avatarPaht);
        updateById(user);
        return BaseResponse.ok("更换头像成功");
    }

    @Override
    public BaseResponse<String> bindEmail(String email, Integer code, Long uid) {
        String key = String.format(RedisKey.USER_EMAIL, uid, email);
        String value = redisService.get(key);
        boolean blank = StringUtils.isBlank(value);
        boolean equals = (String.valueOf(code) + "").equals(value);
        if (blank || !equals) {
            throw new BadRequestException("验证码错误");
        }
        User user = getById(uid).setEmail(email);
        user.setUpdateTime(null);
        boolean update = updateById(user);
        return update ? BaseResponse.ok("绑定成功") : BaseResponse.ok("绑定失败，请稍后再试");
    }

    @Override
    public BaseResponse<String> sendEmailCode(String email, Long uid) {
        User user = getById(uid);
        String code = RandomUtils.number(6);
        String key = String.format(RedisKey.USER_EMAIL, uid, email);
        redisService.setKeyValTTL(key, code, RedisKey.EMAIL_CODE_TIME_OUT);

        // send mail
        Map<String, Object> data = new HashMap<>();
        data.put("code", code);
        data.put("nickname", user.getNickname());

        String templates = "mail_template/mail_bind.ftl";
        String subject = "邮箱绑定验证";
        mailService.sendTemplateMail(email, subject, data, templates);

        return BaseResponse.ok("验证码发送成功，请在" + RedisKey.EMAIL_CODE_TIME_OUT / 60 + "分钟内完成绑定！");
    }

}
