package run.ut.app.api.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.support.BaseResponse;

import java.util.List;

/**
 * @author Lucien
 * @version 1.0
 * @date 2020/5/15 13:45
 */

@Api(value = "管理员上传文件相关Api",tags = "管理员上传文件相关API")
public interface AdminUploadControllerApi {

    @ApiOperation(value = "管理员上传多文件Api")
    BaseResponse<List<String>> uploads(MultipartFile[] files);

}
