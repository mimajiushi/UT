package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.param.TagsParam;
import run.ut.app.model.support.BaseResponse;

import java.util.List;


@Api(value="操作Tags的API",tags = "操作Tags的API")
public interface TagsControllerApi {

    @ApiOperation(value = "获取标签", notes = "parentId为0时则是获取最顶层标签，为其它值时，获取子标签")
    public List<TagsDTO> listTagsByParentId(Integer parentId);
}
