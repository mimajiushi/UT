package run.ut.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.ut.app.api.PostControllerApi;
import run.ut.app.cache.lock.HttpRequestRateLimit;
import run.ut.app.model.enums.RateLimitEnum;
import run.ut.app.model.param.PostParam;
import run.ut.app.model.param.SearchPostParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.PostVO;
import run.ut.app.security.CheckLogin;
import run.ut.app.service.PostsService;
import run.ut.app.service.UserInfoService;

import javax.validation.Valid;


@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("post")
public class PostController extends BaseController implements PostControllerApi {

    private final UserInfoService userInfoService;
    private final PostsService postsService;

    @Override
    @CheckLogin
    @PostMapping("/savePost")
    @HttpRequestRateLimit(limit = RateLimitEnum.RRLimit_1_5)
    public BaseResponse<String> savePost(@RequestBody @Valid PostParam postParam) {
        Long uid = getUid();
        userInfoService.checkUser(uid);
        postParam.setUid(uid);
        return postsService.savePost(postParam);
    }

    @Override
    @CheckLogin
    @PostMapping("/delPost/{postId:\\d+}")
    public BaseResponse<String> delPost(@PathVariable Long postId) {
        return postsService.delPost(postId, getUid());
    }

    @Override
    @CheckLogin
    @PostMapping("/like/{postId:\\d+}")
    public BaseResponse<String> like(@PathVariable Long postId) {
        return postsService.like(postId, getUid());
    }

    @Override
    @CheckLogin
    @PostMapping("/unLike/{postId:\\d+}")
    public BaseResponse<String> unLike(@PathVariable Long postId) {
        return postsService.unLike(postId, getUid());
    }

    @Override
    @CheckLogin
    @PostMapping("/collect/{postId:\\d+}")
    public BaseResponse<String> collect(@PathVariable Long postId) {
        return postsService.collect(postId, getUid());
    }

    @Override
    @CheckLogin
    @PostMapping("/cancelCollect/{postId:\\d+}")
    public BaseResponse<String> cancelCollect(@PathVariable Long postId) {
        return postsService.cancelCollect(postId, getUid());
    }

    @Override
    @CheckLogin
    @GetMapping("list/self/collections")
    public CommentPage<PostVO> listSelfCollection(String title,
                                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                                  @RequestParam(defaultValue = "5") Integer pageSize) {
        Long uid = getUid();
        SearchPostParam searchPostParam = new SearchPostParam().setUid(uid).setTitle(title);
        Page page = new Page(pageNum, pageSize);
        return postsService.listCollectionByParams(page, searchPostParam);
    }
}
