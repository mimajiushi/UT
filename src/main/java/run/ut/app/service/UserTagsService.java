package run.ut.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.domain.UserTags;

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

    @NonNull
    public List<Tags> listByUid(@NonNull Long uid);

    public void deleteByUid(@NonNull Long uid);

}
