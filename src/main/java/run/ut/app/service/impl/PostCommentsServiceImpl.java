package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.PostsMapper;
import run.ut.app.model.domain.PostComments;
import run.ut.app.mapper.PostCommentsMapper;
import run.ut.app.model.domain.Posts;
import run.ut.app.model.param.CommentParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.PostCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  PostCommentsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostCommentsServiceImpl extends ServiceImpl<PostCommentsMapper, PostComments> implements PostCommentsService {

    private final PostsMapper postsMapper;

    @Override
    public BaseResponse<String> commentPost(CommentParam commentParam) {
        int count = postsMapper.selectCount(new QueryWrapper<Posts>().eq("id", commentParam.getPostId()));
        if (count < 1) {
            throw new NotFoundException("帖子不存在");
        }
        PostComments postComments = commentParam.convertTo();
        save(postComments);
        return BaseResponse.ok("评论成功~");
    }

    @Override
    public BaseResponse<String> replyToComments(CommentParam commentParam) {
        int count1 = postsMapper.selectCount(new QueryWrapper<Posts>().eq("id", commentParam.getPostId()));
        int count2 = count(new QueryWrapper<PostComments>().eq("post_id", commentParam.getPostId()));
        if (count1 < 1 || count2 < 1) {
            throw new NotFoundException("帖子不存在");
        }
        PostComments postComments = commentParam.convertTo();
        save(postComments);
        return BaseResponse.ok("回复成功~");
    }
}
