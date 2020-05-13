package run.ut.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.ut.app.api.PostIndexControllerApi;
import run.ut.app.model.param.SearchPostParam;
import run.ut.app.model.vo.PostVO;
import run.ut.app.service.PostsService;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("index")
public class PostIndexController extends BaseController implements PostIndexControllerApi {

    private final PostsService postsService;

    @Override
    @PostMapping("/list/post/")
    public List<PostVO> listPosts(@RequestBody SearchPostParam searchPostParam,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "5") Integer pageSize) {
        Page page = new Page<>(pageNum, pageSize);
        return null;
    }
}
