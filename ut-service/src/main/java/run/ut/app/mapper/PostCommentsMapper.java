package run.ut.app.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.PostComments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import run.ut.app.model.vo.ParentCommentVO;

/**
 * <p>
 *  PostCommentsMapper
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
public interface PostCommentsMapper extends BaseMapper<PostComments> {

    @Update("update post_comments set likes = likes + #{delta} where id = #{commentId} and deleted = 0")
    void incrementLikesCount(@NonNull @Param("commentId") Long commentId, @NonNull @Param("delta") Integer delta);

    @NonNull
    IPage<ParentCommentVO> listCommentToSelfPost(@NonNull Page page, @NonNull @Param("uid") Long uid);
}
