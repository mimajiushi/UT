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
import java.util.stream.Collectors;

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

    @Override
    @GetMapping("listTagsByParentId/{parentId}")
    public List<TagsDTO> listTagsByParentId(@PathVariable Integer parentId) {
        return tagsService.listTagsByParentId(parentId);
    }

    @Override
    @GetMapping("listAllTags")
    public List<TagsDTO> listAllTags() {
        return tagsService.list().stream().map(e -> (TagsDTO) new TagsDTO().convertFrom(e)).collect(Collectors.toList());
    }

}
