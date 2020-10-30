package run.ut.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.Posts;
import run.ut.app.model.param.SearchPostParam;
import run.ut.app.model.vo.PostVO;

/**
 * <p>
 *  PostsMapper
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
public interface PostsMapper extends BaseMapper<Posts> {

    @Deprecated
    IPage<PostVO> listPostsByParams(@NonNull Page page, @Param("param") SearchPostParam searchPostParam);

    IPage<PostVO> listCollectionByParams(@NonNull Page page, @Param("param") SearchPostParam searchPostParam);

    @Update("update posts set likes = likes + #{delta} where id = #{postId} and deleted = 0")
    void incrementLikesCount(@NonNull @Param("postId") Long postId, @NonNull @Param("delta") Integer delta);
}
