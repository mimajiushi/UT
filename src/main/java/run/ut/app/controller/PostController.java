package run.ut.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.PostControllerApi;
import run.ut.app.cache.lock.HttpRequestRateLimit;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.enums.RateLimitEnum;
import run.ut.app.model.param.PostParam;
import run.ut.app.model.support.BaseResponse;
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
        checkUser(uid);
        postParam.setUid(uid);
        return postsService.savePost(postParam);
    }

    /**
     * 检验用户是否通过认证
     * @param uid user's id
     */
    private void checkUser(Long uid) {
        UserInfo userInfo = userInfoService.getOneActivatedByUid(uid);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new AuthenticationException("只有通过认证的用户才能创建团队！");
        }
    }
}
