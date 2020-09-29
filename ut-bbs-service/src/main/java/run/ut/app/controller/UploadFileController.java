package run.ut.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.api.UploadFileControllerApi;
import run.ut.app.exception.FileOperationException;
import run.ut.app.handler.FileHandlers;
import run.ut.app.model.support.UploadResult;
import run.ut.app.security.CheckLogin;
import run.ut.app.service.UserInfoService;
import run.ut.app.utils.ImageUtils;

import javax.annotation.Resource;


@RestController
@Slf4j
public class UploadFileController extends BaseController implements UploadFileControllerApi {

    @Resource private FileHandlers fileHandlers;
    @DubboReference private UserInfoService userInfoService;

    @PostMapping("/user/uploadImage")
    @CheckLogin
    @Override
    public UploadResult upload(@RequestPart("image") MultipartFile image) {
        userInfoService.checkUser(getUid());
        boolean isImage = ImageUtils.isImage(image);
        if (!isImage) {
            throw new FileOperationException("只接受图片格式文件！");
        }
        return fileHandlers.upload(image);
    }
}
