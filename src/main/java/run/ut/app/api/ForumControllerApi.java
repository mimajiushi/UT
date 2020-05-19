package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.dto.ForumDTO;

import java.util.List;

/**
 * @author wenjie
 */
@Api(value = "BBS版块相关API",tags = "BBS版块相关API")
public interface ForumControllerApi {

    @ApiOperation(value = "获取版块list")
    List<ForumDTO> listAllForum();
}
