package run.ut.app.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.config.properties.AttachmentProperties;
import run.ut.app.exception.FileOperationException;
import run.ut.app.model.enums.AttachmentType;
import run.ut.app.model.support.UploadResult;
import run.ut.app.service.OptionsService;

import java.util.Collection;
import java.util.LinkedList;

/**
 * File handler manager.
 *
 * @author johnniang
 * @author wenjie
 */
@Slf4j
@Component
public class FileHandlersImpl implements FileHandlers {

    @DubboReference
    private OptionsService optionsService;

    /**
     * File handler container.
     */
    private final Collection<FileHandler> fileHandlers = new LinkedList<>();

    public FileHandlersImpl(ApplicationContext applicationContext) {
        // Add all file handler
        addFileHandlers(applicationContext.getBeansOfType(FileHandler.class).values());
    }

    /**
     * Uploads files.
     *
     * @param file           multipart file must not be null
     * @return upload result
     * @throws FileOperationException throws when fail to delete attachment or no available file handler to upload it
     */
    @NonNull
    @Override
    public UploadResult upload(@NonNull MultipartFile file) {
        Assert.notNull(file, "Multipart file must not be null");
        AttachmentType attachmentType = getAttachmentType();
        Assert.notNull(attachmentType, "attachmentType file must not be null");

        for (FileHandler fileHandler : fileHandlers) {
            if (fileHandler.supportType(attachmentType)) {
                return fileHandler.upload(file);
            }
        }

        throw new FileOperationException("No available file handler to upload the file").setErrorData(attachmentType);
    }

    /**
     * Deletes attachment.
     *
     * @param filePath File relative path. If file absolute path is: https://xxx.xxx.com/zzz/xxxx.jpg, fill in zzz/xxxx.jpg
     * @throws FileOperationException throws when fail to delete attachment or no available file handler to delete it
     */
    @Override
    public void delete(@NonNull String filePath) {
        Assert.notNull(filePath, "filePath must not be null");

        for (FileHandler fileHandler : fileHandlers) {
            if (fileHandler.supportType(AttachmentType.QINIUOSS)) {
                // Delete the file
                fileHandler.delete(filePath);
                return;
            }
        }

        throw new FileOperationException("No available file handler to delete the file").setErrorData(filePath + "is not found or deleted");
    }

    /**
     * Adds file handlers.
     *
     * @param fileHandlers file handler collection
     * @return current file handlers
     */
    @NonNull
    @Override
    public FileHandlers addFileHandlers(@Nullable Collection<FileHandler> fileHandlers) {
        if (!CollectionUtils.isEmpty(fileHandlers)) {
            this.fileHandlers.addAll(fileHandlers);
        }
        return this;
    }

    /**
     * Get attachment type from options.
     *
     * @return attachment type
     */
    @NonNull
    @Override
    public AttachmentType getAttachmentType() {
        return optionsService.getEnumByPropertyOrDefault(AttachmentProperties.ATTACHMENT_TYPE, AttachmentType.class, AttachmentType.LOCAL);
    }
}
