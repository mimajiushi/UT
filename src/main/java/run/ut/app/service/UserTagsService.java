package run.ut.app.service;

import run.ut.app.model.domain.Tags;
import run.ut.app.model.domain.UserTags;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  UserTagsService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-11
 */
public interface UserTagsService extends IService<UserTags> {

    public List<Tags> listByUid(Long uid);

    public void deleteByUid(Long uid);

}
