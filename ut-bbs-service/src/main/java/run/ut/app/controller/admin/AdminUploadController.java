package run.ut.app.controller.admin;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.api.admin.AdminUploadControllerApi;
import run.ut.app.handle.FileHandlers;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.UploadResult;
import run.ut.app.security.CheckAuthorization;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucien
 * @version 1.0
 * @date 2020/5/15 13:49
 */

@RestController
@Slf4j
@RequestMapping("admin")
@CheckAuthorization(roles = {UserRolesEnum.ROLE_ADMIN, UserRolesEnum.ROLE_SPONSOR})
public class AdminUploadController implements AdminUploadControllerApi {

    @DubboReference
    private FileHandlers fileHandlers;

    @PostMapping("uploads")
    @Override
    public BaseResponse<List<String>> uploads(@NonNull MultipartFile[] file) {
        ArrayList<String> list = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            UploadResult upload = fileHandlers.upload(multipartFile);
            list.add(upload.getFilePath());
        }
        return BaseResponse.ok("上传成功！", list);
    }
}
