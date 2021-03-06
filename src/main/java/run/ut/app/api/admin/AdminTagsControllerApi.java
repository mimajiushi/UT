package run.ut.app.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.param.TagsParam;
import run.ut.app.model.support.BaseResponse;

import java.util.List;

@Api(value = "管理员操作标签API",tags = "管理员操作标签API")
public interface AdminTagsControllerApi {

    @ApiOperation(value = "添加/更新tags", notes = "该接口需要管理员权限")
    public BaseResponse<TagsDTO> saveTag(TagsParam tagsParam);

    @ApiOperation(value = "删除tags", notes = "该接口需要管理员权限")
    public BaseResponse<String> delTags(List<Long> tagIds);
}
