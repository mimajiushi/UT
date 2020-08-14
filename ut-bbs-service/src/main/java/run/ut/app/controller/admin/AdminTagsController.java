package run.ut.app.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.admin.AdminTagsControllerApi;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.param.TagsParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.security.CheckAuthorization;
import run.ut.app.service.TagsService;

import javax.validation.Valid;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("admin")
@CheckAuthorization(roles = UserRolesEnum.ROLE_ADMIN)
public class AdminTagsController implements AdminTagsControllerApi {

    @DubboReference
    private TagsService tagsService;

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
