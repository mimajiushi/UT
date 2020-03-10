package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.dto.DataAreaDTO;
import run.ut.app.model.dto.DataSchoolDTO;
import run.ut.app.model.support.UploadResult;

import java.util.List;

@Api(value="上传文件测试API",tags = "上传文件测试API")
public interface UploadFileTestControllerApi {

    @ApiOperation("上传文件")
    public UploadResult upload(MultipartFile file);

}
