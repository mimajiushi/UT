package run.ut.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.UserInfoDTO;
import run.ut.app.model.param.UserInfoParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;

/**
 * <p>
 *  UserInfoService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-09
 */
public interface UserInfoService extends IService<UserInfo> {

    @NonNull
    BaseResponse<UserInfoDTO> applyForCertification(@NonNull UserInfoParam userInfoParam);

    @NonNull
    UserInfo getOneActivatedByUid(@NonNull Long uid);

    @NonNull
    BaseResponse<String> verifyUserInfo(@NonNull Integer id, @NonNull Integer status, @Nullable String reason);

    @NonNull
    CommentPage<UserInfoDTO> listUserInfoByParam(@NonNull UserInfoParam userInfoParam, @NonNull Page<UserInfo> page);
}
