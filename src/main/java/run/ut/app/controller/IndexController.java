package run.ut.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.ut.app.api.IndexControllerApi;
import run.ut.app.model.param.SearchStudentParam;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.StudentVO;
import run.ut.app.service.IndexService;


@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("index")
public class IndexController implements IndexControllerApi {

    private final IndexService indexService;

    @Override
    @GetMapping("listStudentByParam")
    public CommentPage<StudentVO> listStudentByParam(SearchStudentParam searchStudentParam,
                                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        Page page = new Page<>(pageNum, pageSize);

        return indexService.listStudentByParam(searchStudentParam, page);
    }

    @Override
    @GetMapping("/student/{uid}")
    public StudentVO showStudentInfo(@PathVariable Long uid) {
        return indexService.showStudentPage(uid);
    }

}
