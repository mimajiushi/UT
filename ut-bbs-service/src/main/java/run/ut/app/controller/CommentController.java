package run.ut.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import run.ut.app.api.CommentControllerApi;
import run.ut.app.model.domain.PostComments;
import run.ut.app.model.param.CommentParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.ChildCommentVO;
import run.ut.app.model.vo.ParentCommentVO;
import run.ut.app.security.CheckLogin;
import run.ut.app.service.PostCommentsService;
import run.ut.app.service.UserInfoService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wenjie
 */

@RestController
@Slf4j
@RequestMapping("comment")
public class CommentController extends BaseController implements CommentControllerApi {

    @DubboReference private PostCommentsService postCommentsService;
    @DubboReference private UserInfoService userInfoService;

    @Override
    @PostMapping("toPost")
    @CheckLogin
    public BaseResponse<String> commentPost(@RequestBody @Valid CommentParam commentParam) {
        Long uid = getUid();
        userInfoService.checkUser(uid);
        commentParam.setFromUid(uid);
        return postCommentsService.commentPost(commentParam);
    }

    @Override
    @PostMapping("replyToComments")
    @CheckLogin
    public BaseResponse<String> replyToComments(@RequestBody @Valid CommentParam commentParam) {
        Assert.notNull(commentParam.getParentCommentId(), "parent comment id must not be null.");
        Assert.notNull(commentParam.getToUid(), "to uid must not be null.");
        Long uid = getUid();
        userInfoService.checkUser(uid);
        commentParam.setFromUid(uid);
        return postCommentsService.replyToComments(commentParam);
    }

    @Override
    @PostMapping("delComment/{commentId:\\d+}")
    @CheckLogin
    public BaseResponse<String> delComment(@PathVariable Long commentId) {
        return postCommentsService.delComment(getUid(), commentId);
    }

    @Override
    @PostMapping("/likesComment/{commentId:\\d+}")
    @CheckLogin
    public BaseResponse<String> likesComment(@PathVariable Long commentId) {
        return postCommentsService.likesComment(getUid(), commentId);
    }

    @Override
    @PostMapping("/cancelLikesComment/{commentId:\\d+}")
    @CheckLogin
    public BaseResponse<String> cancelLikesComment(@PathVariable Long commentId) {
        return postCommentsService.cancelLikesComment(getUid(), commentId);
    }

    @Override
    @GetMapping("/listCommentOfPost/{postId:\\d+}")
    public CommentPage<ParentCommentVO> listCommentOfPost(@PathVariable Long postId,
                                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        Long operatorUid = getUidFromToken();
        Page<PostComments> page = new Page<>(pageNum, pageSize);
        return postCommentsService.listCommentOfPost(page, postId, operatorUid);
    }

    @Override
    @GetMapping("listCommentToSelf")
    @CheckLogin
    public CommentPage<ChildCommentVO> listCommentToSelf(@RequestParam(defaultValue = "1") Integer pageNum,
                                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PostComments> page = new Page<>(pageNum, pageSize);
        return postCommentsService.listCommentToSelf(getUid(), page);
    }

    @Override
    @GetMapping("listCommentToSelfPost")
    @CheckLogin
    public CommentPage<ParentCommentVO> listCommentToSelfPost(@RequestParam(defaultValue = "1") Integer pageNum,
                                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PostComments> page = new Page<>(pageNum, pageSize);
        return postCommentsService.listCommentToSelfPost(getUid(), page);
    }

    @Override
    @GetMapping("getCommentUnreadCount")
    @CheckLogin
    public List<Integer> getCommentUnreadCount() {
        return postCommentsService.getCommentUnreadCount(getUid());
    }

    @Override
    @GetMapping("clearUnreadCount")
    @CheckLogin
    public BaseResponse<String> clearUnreadCount() {
        return postCommentsService.clearUnreadCount(getUid());
    }
}
