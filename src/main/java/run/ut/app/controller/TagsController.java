package run.ut.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.ut.app.api.TagsControllerApi;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.param.TagsParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.security.CheckAuthorization;
import run.ut.app.service.TagsService;

import javax.validation.Valid;
import java.util.List;

/**
 * Manager tags controller, just allow admin
 *
 * @author wenjie
 */


@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("tags")
public class TagsController implements TagsControllerApi {

    private final TagsService tagsService;


    @PostMapping("saveTag")
    @CheckAuthorization("ROLE_ADMIN")
    @Override
    public BaseResponse<TagsDTO> saveTag(@Valid TagsParam tagsParam) {
        return tagsService.saveTag(tagsParam);
    }

    @Override
    @GetMapping("listTagsByParentId/{parentId}")
    public List<TagsDTO> listTagsByParentId(@PathVariable Integer parentId) {
        return tagsService.listTagsByParentId(parentId);
    }

}
