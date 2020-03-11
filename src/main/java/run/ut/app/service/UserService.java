package run.ut.app.service;

import run.ut.app.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.dto.TagsDTO;

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

    List<TagsDTO> saveUserTags(Long uid, String[] tagIds) throws Exception;
}
