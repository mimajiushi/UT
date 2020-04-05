package run.ut.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.TagsControllerApi;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.service.TagsService;
import run.ut.app.utils.BeanUtils;

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

    @Override
    @GetMapping("listTagsByParentId/{parentId}")
    public List<TagsDTO> listTagsByParentId(@PathVariable Integer parentId) {
        return tagsService.listTagsByParentId(parentId);
    }

    @Override
    @GetMapping("listAllTags")
    public List<TagsDTO> listAllTags() {
        return BeanUtils.transformFromInBatch(tagsService.list(), TagsDTO.class);
}

}
