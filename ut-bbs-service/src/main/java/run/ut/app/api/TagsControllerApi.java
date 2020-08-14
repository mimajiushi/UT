package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.dto.TagsDTO;

import java.util.List;

/**
 * @author wenjie
 */

@Api(value = "操作Tags的API",tags = "操作Tags的API")
public interface TagsControllerApi {

    @ApiOperation(value = "获取某一层级的标签", notes = "parentId为0时则是获取最顶层标签，为其它值时，获取子标签")
    public List<TagsDTO> listTagsByParentId(Integer parentId);

    @ApiOperation(value = "获取所有标签")
    public List<TagsDTO> listAllTags();
}
