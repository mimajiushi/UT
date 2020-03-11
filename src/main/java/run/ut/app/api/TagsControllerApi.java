package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.param.TagsParam;
import run.ut.app.model.support.BaseResponse;

import java.util.List;


@Api(value="操作Tags的API",tags = "操作Tags的API")
public interface TagsControllerApi {

    @ApiOperation(value = "添加/更新tags", notes = "该接口需要管理员权限")
    public BaseResponse<TagsDTO> saveTag(TagsParam tagsParam);

    @ApiOperation(value = "获取标签", notes = "parentId为0时则是获取最顶层标签，为其它值时，获取子标签")
    public List<TagsDTO> listTagsByParentId(Integer parentId);
}
