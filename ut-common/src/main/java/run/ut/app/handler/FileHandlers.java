package run.ut.app.handler;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.enums.AttachmentType;
import run.ut.app.model.support.UploadResult;

import java.io.IOException;
import java.util.Collection;

public interface FileHandlers {
    @NonNull
    UploadResult upload(@NonNull MultipartFile file);

    void delete(@NonNull String filePath);

    @NonNull
    FileHandlers addFileHandlers(@Nullable Collection<FileHandler> fileHandlers);

    @NonNull
    AttachmentType getAttachmentType();
}
