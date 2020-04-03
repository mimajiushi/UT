package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.support.UploadResult;


@Api(value = "上传文件测试API",tags = "上传文件测试API")
public interface UploadFileTestControllerApi {

    @ApiOperation("上传文件")
    public UploadResult upload(MultipartFile file);

}
