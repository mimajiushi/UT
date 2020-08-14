package run.ut.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.ut.app.api.PostIndexControllerApi;
import run.ut.app.model.param.SearchPostParam;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.PostVO;
import run.ut.app.service.PostsService;


@RestController
@Slf4j
@RequestMapping("index")
public class PostIndexController extends BaseController implements PostIndexControllerApi {

    @DubboReference private PostsService postsService;

    @Override
    @GetMapping("/list/post")
    public CommentPage<PostVO> listPosts(SearchPostParam searchPostParam,
                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "5") Integer pageSize) {
        searchPostParam.setOperatorUid(getUidFromToken());
        Page page = new Page<>(pageNum, pageSize);
        return postsService.listPostsByParams(searchPostParam, page);
    }

    @Override
    @GetMapping("/post/detail/{postId:\\d+}")
    public PostVO postDetail(@PathVariable Long postId) {
        Long operatorUid = getUidFromToken();
        return postsService.postDetail(operatorUid, postId);
    }
}
