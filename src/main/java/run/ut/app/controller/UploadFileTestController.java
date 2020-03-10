package run.ut.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.api.UploadFileTestControllerApi;
import run.ut.app.handler.FileHandlers;
import run.ut.app.model.support.UploadResult;


@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UploadFileTestController implements UploadFileTestControllerApi {

    private final FileHandlers fileHandlers;

    @PostMapping("test/upload")
    @Override
    public UploadResult upload(@RequestPart("file") MultipartFile file) {
        return fileHandlers.upload(file);
    }
}
