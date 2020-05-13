package run.ut.app.service;

import org.springframework.lang.NonNull;
import run.ut.app.model.domain.Posts;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.param.PostParam;
import run.ut.app.model.support.BaseResponse;

/**
 * <p>
 *  PostsService
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
public interface PostsService extends IService<Posts> {

    /**
     * Insert if id is empty, otherwise update.
     *
     * @param postParam   post params
     * @return            ok result with message
     */
    @NonNull
    BaseResponse<String> savePost(@NonNull PostParam postParam);
}
