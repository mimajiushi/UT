package run.ut.app.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.ut.app.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.param.WeChatLoginParam;
import run.ut.app.model.support.WeChatResponse;

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

    @NonNull
    List<TagsDTO> saveUserTags(@NonNull Long uid, @Nullable String[] tagIds) throws Exception;

    /**
     * If the user is not registered, it will be automatically registered
     */
    @NonNull
    UserDTO wechatLogin(@NonNull WeChatLoginParam weChatLoginParam, @NonNull WeChatResponse weChatResponse);

    @NonNull
    User getUserByOpenId(@NonNull String openId);
}
