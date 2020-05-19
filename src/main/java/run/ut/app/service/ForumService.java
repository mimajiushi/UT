package run.ut.app.service;

import org.springframework.lang.NonNull;
import run.ut.app.model.domain.Forum;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.param.ForumParam;
import run.ut.app.model.support.BaseResponse;

import java.util.List;

/**
 * <p>
 * ForumService
 * </p>
 *
 * @author wenjie
 * @since 2020-05-19
 */
public interface ForumService extends IService<Forum> {

    /**
     * Insert or update forum
     *
     * @param forumParam   params
     * @return             ok result with message
     */
    @NonNull
    BaseResponse<String> saveForum(@NonNull ForumParam forumParam);

    /**
     * Remove forumId by id
     *
     * @param forumId   forum id
     * @return          ok result with message
     */
    @NonNull
    BaseResponse<String> removeForum(@NonNull Long forumId);
}
