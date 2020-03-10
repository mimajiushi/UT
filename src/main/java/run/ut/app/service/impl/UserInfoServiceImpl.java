package run.ut.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.handler.FileHandlers;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.model.dto.UserInfoDTO;
import run.ut.app.model.enums.DegreeEnum;
import run.ut.app.model.enums.UserInfoStatusEnum;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.param.UserInfoParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.UploadResult;
import run.ut.app.service.DataAreaService;
import run.ut.app.service.DataSchoolService;
import run.ut.app.service.UserInfoService;

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

    @Override
    @Transactional
    public BaseResponse<UserInfoDTO> applyForCertification(UserInfoParam userInfoParam,
                                                           MultipartFile credentialsPhotoFront,
                                                           MultipartFile credentialsPhotoReverse){

        UserInfo userInfo = userInfoParam.convertTo();
        UploadResult upload1 = fileHandlers.upload(credentialsPhotoFront);
        UploadResult upload2 = fileHandlers.upload(credentialsPhotoReverse);
        userInfo.setCredentialsPhotoFront(upload1.getFilePath());
        userInfo.setCredentialsPhotoReverse(upload2.getFilePath());
        userInfo.setStatus(UserInfoStatusEnum.WAITING);
        userInfo.setRole(UserRolesEnum.getByType(userInfoParam.getRole()));
        userInfo.setDegreeId(DegreeEnum.getByType(userInfoParam.getDegreeId()));

        save(userInfo);

        UserInfoDTO userInfoDTO = new UserInfoDTO().convertFrom(userInfo);
        userInfoDTO.setSchool(dataSchoolService.getById(userInfo.getSchoolId()).getName());
        userInfoDTO.setArea(dataAreaService.getById(userInfo.getAreaId()).getName());

        return BaseResponse.ok("提交资料成功！请耐心等待审核", userInfoDTO);
    }

}
