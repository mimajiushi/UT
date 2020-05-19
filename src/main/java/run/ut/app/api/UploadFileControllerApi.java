package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.support.UploadResult;

/**
 * @author wenjie
 */

@Api(value = "上传文件API",tags = "上传文件API")
public interface UploadFileControllerApi {

    @ApiOperation(value = "上传文件", notes = "只允许通过认证的用户上传")
    public UploadResult upload(MultipartFile file);

}
