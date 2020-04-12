package run.ut.app.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.admin.AdminTagsControllerAPI;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.param.TagsParam;
import run.ut.app.model.support.AuthorizeRoles;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.security.CheckAuthorization;
import run.ut.app.service.TagsService;

import javax.validation.Valid;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("admin")
@CheckAuthorization(roles = AuthorizeRoles.ROLE_ADMIN)
public class AdminTagsController implements AdminTagsControllerAPI {
    private final TagsService tagsService;

    @PostMapping("saveTag")
    @Override
    public BaseResponse<TagsDTO> saveTag(@Valid TagsParam tagsParam) {
        return tagsService.saveTag(tagsParam);
    }

    @PostMapping("delTags")
    @Override
    public BaseResponse<String> delTags(@RequestBody List<Long> tagIds) {
        tagsService.removeByIds(tagIds);
        return BaseResponse.ok("删除完成");
    }
}
