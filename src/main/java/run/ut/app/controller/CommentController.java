package run.ut.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.CommentControllerApi;
import run.ut.app.model.param.CommentParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.security.CheckLogin;
import run.ut.app.service.PostCommentsService;
import run.ut.app.service.UserInfoService;

import javax.validation.Valid;

/**
 * @author wenjie
 */

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("comment")
public class CommentController extends BaseController implements CommentControllerApi {

    private final PostCommentsService postCommentsService;
    private final UserInfoService userInfoService;

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
}
