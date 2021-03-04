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

import java.io.IOException;

/**
 * <p>
 *  UserInfoService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-09
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * Apply for identity authentication
     *
     * @param userInfoParam user info
     * @return ok result with UserInfoDTO
     */
    @NonNull
    BaseResponse<UserInfoDTO> applyForCertification(@NonNull UserInfoParam userInfoParam);

    /**
     * Get approved user info by uid
     *
     * @param uid uid
     * @return UserInfo
     */
    @NonNull
    UserInfo getOneActivatedByUid(@NonNull Long uid);

    /**
     * Verify identity authentication
     *
     * @param id        user info id
     * @param status    status
     * @param reason    reason
     * @return ok result with message
     */
    @NonNull
    BaseResponse<String> verifyUserInfo(@NonNull String id, @NonNull Integer status, @Nullable String reason);

    /**
     *
     * @param userInfoParam    params
     * @param page             Paging object of mybatis
     * @return serInfoDTO
     * @throws IOException IOException
     */
    @NonNull
    CommentPage<UserInfoDTO> listUserInfoByParam(@NonNull UserInfoParam userInfoParam, @NonNull Page<UserInfo> page) throws IOException;

    /**
     * 检验用户是否通过认证
     * @param uid user's id
     */
    void checkUser(@NonNull Long uid);
}
