package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.config.redis.RedisConfig;
import run.ut.app.exception.AlreadyExistsException;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.handler.FileHandlers;
import run.ut.app.mapper.UserInfoMapper;
import run.ut.app.mapper.UserMapper;
import run.ut.app.model.domain.DataSchool;
import run.ut.app.model.domain.User;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.UserInfoDTO;
import run.ut.app.model.enums.DegreeEnum;
import run.ut.app.model.enums.UserInfoStatusEnum;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.param.UserInfoParam;
import run.ut.app.model.support.BASE64DecodedMultipartFile;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.support.UploadResult;
import run.ut.app.service.DataAreaService;
import run.ut.app.service.DataSchoolService;
import run.ut.app.service.RedisService;
import run.ut.app.service.UserInfoService;
import run.ut.app.utils.BeanUtils;
import run.ut.app.utils.JsonUtils;

import java.io.IOException;
import java.util.List;

import static run.ut.app.model.enums.UserRolesEnum.getByType;

/**
 * <p>
 *  UserInfoServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-09
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    private final FileHandlers fileHandlers;
    private final DataSchoolService dataSchoolService;
    private final DataAreaService dataAreaService;
    private final UserMapper userMapper;
    private final UserInfoMapper userInfoMapper;
    private final RedisService redisService;

    @Override
    @Transactional
    public BaseResponse<UserInfoDTO> applyForCertification(UserInfoParam userInfoParam) {

        int count = count(new QueryWrapper<UserInfo>()
                .eq("status", UserInfoStatusEnum.WAITING.getType()).eq("uid", userInfoParam.getUid()));
        if (count > 0) {
            throw new AlreadyExistsException("当前还有申请未处理，请耐心等待审核完成后再申请！");
        }

        User user = userMapper.selectById(userInfoParam.getUid());
        if ((user.getRoles() & userInfoParam.getRole()) == userInfoParam.getRole()) {
            throw new AlreadyExistsException("申请的角色重复了！");
        }

        MultipartFile credentialsPhotoFront = BASE64DecodedMultipartFile.base64ToMultipart(userInfoParam.getCredentialsPhotoFront());
        MultipartFile credentialsPhotoReverse = BASE64DecodedMultipartFile.base64ToMultipart(userInfoParam.getCredentialsPhotoReverse());

        UserInfo userInfo = userInfoParam.convertTo();
        UploadResult upload1 = fileHandlers.upload(credentialsPhotoFront);
        UploadResult upload2 = fileHandlers.upload(credentialsPhotoReverse);
        userInfo.setCredentialsPhotoFront(upload1.getFilePath())
                .setCredentialsPhotoReverse(upload2.getFilePath())
                .setStatus(UserInfoStatusEnum.WAITING)
                .setRole(getByType(userInfoParam.getRole()).getType())
                .setDegreeId(DegreeEnum.getByType(userInfoParam.getDegreeId()));

        save(userInfo);

        UserInfoDTO userInfoDTO = new UserInfoDTO().convertFrom(userInfo);
        userInfoDTO.setSchool(dataSchoolService.getById(userInfo.getSchoolId()).getName())
                .setArea(dataAreaService.getById(userInfo.getAreaId()).getName());

        return BaseResponse.ok("提交资料成功！请耐心等待审核", userInfoDTO);
    }

    @Override
    public UserInfo getOneActivatedByUid(Long uid) {
        return getOne(new QueryWrapper<UserInfo>().eq("uid", uid).eq("status", UserInfoStatusEnum.PASS.getType()));
    }

    @Override
    @Transactional
    public BaseResponse<String> verifyUserInfo(Integer id, Integer status, String reason) {

        UserInfo userInfo = getById(id);
        User user = userMapper.selectById(userInfo.getUid());

        // If the audit fails
        if (status == UserInfoStatusEnum.FAIL.getType()) {
            userInfo.setStatus(UserInfoStatusEnum.FAIL);
            if (!StringUtils.isBlank(reason)) {
                userInfo.setReason(reason);
            } else {
                throw new BadRequestException("不通过原因必须填写！");
            }
            updateById(userInfo);
            return BaseResponse.ok("审核完成，结果：" + userInfo.getStatus().getName());
        }

        // If approved
        // delete old userinfo
        remove(new QueryWrapper<UserInfo>()
                .eq("uid", userInfo.getUid())
                .eq("status", UserInfoStatusEnum.PASS.getType()));
        // update new userinfo and user
        user.setRoles(user.getRoles() + userInfo.getRole());
        userInfo.setRole(user.getRoles());
        userInfo.setStatus(UserInfoStatusEnum.PASS);
        updateById(userInfo);
        userMapper.updateById(user);
        return BaseResponse.ok("审核完成，结果：" + userInfo.getStatus().getName());
    }

    @Override
    public CommentPage<UserInfoDTO> listUserInfoByParam(UserInfoParam userInfoParam, Page<UserInfo> page) throws IOException {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        Integer status = userInfoParam.getStatus();
        if (null != status) {
            userInfoQueryWrapper.eq("status", UserInfoStatusEnum.getByType(status));
        }
        userInfoQueryWrapper.orderByDesc("create_time");
        Page<UserInfo> userInfoPage = userInfoMapper.selectPage(page, userInfoQueryWrapper);
        List<UserInfoDTO> userInfoDTOList = BeanUtils.transformFromInBatch(userInfoPage.getRecords(), UserInfoDTO.class);
        for (UserInfoDTO userInfoDTO : userInfoDTOList) {
            List<String> roles = UserRolesEnum.getRoles(userInfoDTO.getRole());
            String schoolJson = redisService.get(RedisConfig.SCHOOL_DATA_PREFIX + "::" + userInfoDTO.getSchoolId());
            DataSchool school = JsonUtils.jsonToObject(schoolJson, DataSchool.class);
            userInfoDTO.setRoles(roles);
            userInfoDTO.setSchool(school.getName());
        }

        return new CommentPage<>(userInfoPage.getTotal(), userInfoDTOList);
    }

    /**
     * 检验用户是否通过认证
     * @param uid user's id
     */
    @Override
    public void checkUser(Long uid) {
        UserInfo userInfo = getOneActivatedByUid(uid);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new AuthenticationException("只有通过认证的用户才能创建团队！");
        }
    }

}
