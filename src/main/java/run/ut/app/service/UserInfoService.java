package run.ut.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.dto.UserInfoDTO;
import run.ut.app.model.param.UserInfoParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;

import java.util.List;

/**
 * <p>
 *  UserInfoService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-09
 */
public interface UserInfoService extends IService<UserInfo> {

    BaseResponse<UserInfoDTO> applyForCertification(UserInfoParam userInfoParam,
                                                    MultipartFile credentialsPhotoFront,
                                                    MultipartFile credentialsPhotoReverse);

    UserInfo getOneActivatedByUid(Long uid);

    BaseResponse<String> verifyUserInfo(Integer id, Integer status, String reason);

    CommentPage<UserInfoDTO> listUserInfoByParam(UserInfoParam userInfoParam, Page<UserInfo> page);
}
