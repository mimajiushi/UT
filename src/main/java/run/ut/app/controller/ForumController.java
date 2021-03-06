package run.ut.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.ForumControllerApi;
import run.ut.app.model.domain.Forum;
import run.ut.app.model.dto.ForumDTO;
import run.ut.app.service.ForumService;
import run.ut.app.utils.BeanUtils;

import java.util.List;

/**
 * @author wenjie
 * @date 2020-5-19
 */

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("forum")
public class ForumController extends BaseController implements ForumControllerApi {

    private final ForumService forumService;

    @Override
    @GetMapping("/list/all")
    public List<ForumDTO> listAllForum() {
        List<Forum> forumList = forumService.list(null);
        return BeanUtils.transformFromInBatch(forumList, ForumDTO.class);
    }
}
