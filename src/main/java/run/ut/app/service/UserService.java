package run.ut.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.domain.User;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.param.EmailLoginParam;
import run.ut.app.model.param.WeChatLoginParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.WeChatResponse;
import run.ut.app.model.vo.StudentVO;

import java.util.List;

/**
 * <p>
 * UserService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-08
 */
public interface UserService extends IService<User> {

    /**
     * save user tags
     *
     * @param uid           uid
     * @param tagIds        tag ids
     * @return              TagsDTO list
     * @throws Exception    exception
     */
    @NonNull
    List<TagsDTO> saveUserTags(@NonNull Long uid, @Nullable String[] tagIds) throws Exception;

    /**
     * Get user info by uid
     *
     * @param uid  uid
     * @return     StudentVO
     */
    @NonNull
    StudentVO showSelfPage(@NonNull Long uid);

    /**
     * If the user is not registered, it will be automatically registered
     *
     * @param weChatLoginParam  wechat login parameters
     * @param weChatResponse    parameters returned by wechat
     * @return                  UserDTO
     */
    @NonNull
    UserDTO wechatLogin(@NonNull WeChatLoginParam weChatLoginParam, @NonNull WeChatResponse weChatResponse);

    /**
     * Login by email and other params
     * @param emailLoginParam email and code
     * @return user info
     */
    @NonNull
    UserDTO loginByEmail(@NonNull EmailLoginParam emailLoginParam);

    /**
     * Get user by openId
     *
     * @param openId openId
     * @return       User
     */
    @NonNull
    User getUserByOpenId(@NonNull String openId);


    /**
     * Get user by email
     *
     * @param email  email
     * @return       User
     */
    @NonNull
    User getUserByEmail(@NonNull String email);

    /**
     * Update user's avatar
     *
     * @param uid       uid
     * @param avatar    avatar (MultipartFile)
     * @return ok result with message
     */
    @NonNull
    BaseResponse<String> updateUserAvatar(@NonNull Long uid, @NonNull MultipartFile avatar);

    /**
     * User bind email
     * @param email    email address
     * @param code     code
     * @param uid      uid
     * @return         ok result with message
     */
    @NonNull
    BaseResponse<String> bindEmail(@NonNull String email, @NonNull Integer code, @NonNull Long uid);

    /**
     * Send code to email
     * @param email    email address
     * @param uid      uid
     * @return         ok result with message
     */
    @NonNull
    BaseResponse<String> sendEmailCode(@NonNull String email, @NonNull Long uid);

    /**
     * Send code to email
     * @param email    email address
     * @return         ok result with message
     */
    @NonNull
    BaseResponse<String> sendEmailCode(@NonNull String email);
}
