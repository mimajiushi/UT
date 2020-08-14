package run.ut.app.handler;


import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.exception.FileOperationException;
import run.ut.app.handle.FileHandler;
import run.ut.app.model.enums.AttachmentType;
import run.ut.app.model.properties.QiniuOssProperties;
import run.ut.app.model.support.QiNiuPutSet;
import run.ut.app.model.support.UploadResult;
import run.ut.app.service.OptionsService;
import run.ut.app.utils.FilenameUtils;
import run.ut.app.utils.ImageUtils;
import run.ut.app.utils.JsonUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


import static run.ut.app.handle.FileHandler.isImageType;

/**
 * Qiniu oss file handler.
 *
 * @author johnniang
 * @author ryanwang
 * @author wenjie
 * @date 2019-03-27
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QiniuOssFileHandler implements FileHandler {

    private final OptionsService optionsService;

    @Override
    public UploadResult upload(MultipartFile file) {
        Assert.notNull(file, "Multipart file must not be null");

        // Get all config
        Zone zone = optionsService.getQnYunZone();
        String accessKey = optionsService.getByPropertyOfNonNull(QiniuOssProperties.OSS_ACCESS_KEY).toString();
        String secretKey = optionsService.getByPropertyOfNonNull(QiniuOssProperties.OSS_SECRET_KEY).toString();
        String bucket = optionsService.getByPropertyOfNonNull(QiniuOssProperties.OSS_BUCKET).toString();
        String protocol = optionsService.getByPropertyOfNonNull(QiniuOssProperties.OSS_PROTOCOL).toString();
        String domain = optionsService.getByPropertyOfNonNull(QiniuOssProperties.OSS_DOMAIN).toString();
        String source = optionsService.getByPropertyOrDefault(QiniuOssProperties.OSS_SOURCE, String.class, "");
        String styleRule = optionsService.getByPropertyOrDefault(QiniuOssProperties.OSS_STYLE_RULE, String.class, "");
        String thumbnailStyleRule = optionsService.getByPropertyOrDefault(QiniuOssProperties.OSS_THUMBNAIL_STYLE_RULE, String.class, "");

        // Create configuration
        Configuration configuration = new Configuration(zone);

        // Create auth
        Auth auth = Auth.create(accessKey, secretKey);
        // Build put plicy
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"size\":$(fsize), " +
                "\"width\":$(imageInfo.width), " +
                "\"height\":$(imageInfo.height)," +
                " \"key\":\"$(key)\", " +
                "\"hash\":\"$(etag)\"}");
        // Get upload token
        String uploadToken = auth.uploadToken(bucket, null, 3600, putPolicy);

        // Create temp path
        Path tmpPath = Paths.get(System.getProperty("java.io.tmpdir"), bucket);

        StringBuilder basePath = new StringBuilder(protocol)
                .append(domain)
                .append("/");

        try {
            String basename = FilenameUtils.getBasename(file.getOriginalFilename());
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String timestamp = String.valueOf(System.currentTimeMillis());
            StringBuilder upFilePath = new StringBuilder();
            if (StringUtils.isNotEmpty(source)) {
                upFilePath.append(source)
                        .append("/");
            }
            upFilePath.append(basename)
                    .append("_")
                    .append(timestamp)
                    .append(".")
                    .append(extension);

            // Get file recorder for temp directory
            FileRecorder fileRecorder = new FileRecorder(tmpPath.toFile());
            // Get upload manager
            UploadManager uploadManager = new UploadManager(configuration, fileRecorder);
            // Put the file
            Response response = uploadManager.put(file.getInputStream(), upFilePath.toString(), uploadToken, null, null);

            log.debug("QnYun response: [{}]", response.toString());
            log.debug("QnYun response body: [{}]", response.bodyString());

            response.jsonToObject(QiNiuPutSet.class);

            // Convert response
            QiNiuPutSet putSet = JsonUtils.jsonToObject(response.bodyString(), QiNiuPutSet.class);

            // Get file full path
            String filePath = StringUtils.join(basePath.toString(), upFilePath.toString());

            // Build upload result
            UploadResult result = new UploadResult();
            result.setFilename(basename);
            result.setFilePath(StringUtils.isBlank(styleRule) ? filePath : filePath + styleRule);
            result.setKey(upFilePath.toString());
            result.setSuffix(extension);
            result.setWidth(putSet.getWidth());
            result.setHeight(putSet.getHeight());
            result.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())));
            result.setSize(file.getSize());

            if (isImageType(result.getMediaType())) {
                if (ImageUtils.EXTENSION_ICO.equals(extension)) {
                    result.setThumbPath(filePath);
                } else {
                    result.setThumbPath(StringUtils.isBlank(thumbnailStyleRule) ? filePath : filePath + thumbnailStyleRule);
                }
            }

            return result;
        } catch (IOException e) {
            if (e instanceof QiniuException) {
                log.error("QnYun error response: [{}]", ((QiniuException) e).response);
            }

            throw new FileOperationException("上传附件 " + file.getOriginalFilename() + " 到七牛云失败", e);
        }
    }

    @Override
    public void delete(String key) {
        Assert.notNull(key, "File key must not be blank");

        // Get all config
        Zone zone = optionsService.getQnYunZone();
        String accessKey = optionsService.getByPropertyOfNonNull(QiniuOssProperties.OSS_ACCESS_KEY).toString();
        String secretKey = optionsService.getByPropertyOfNonNull(QiniuOssProperties.OSS_SECRET_KEY).toString();
        String bucket = optionsService.getByPropertyOfNonNull(QiniuOssProperties.OSS_BUCKET).toString();

        // Create configuration
        Configuration configuration = new Configuration(zone);

        // Create auth
        Auth auth = Auth.create(accessKey, secretKey);

        BucketManager bucketManager = new BucketManager(auth, configuration);

        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException e) {
            log.error("QnYun error response: [{}]", e.response);
            throw new FileOperationException("附件 " + key + " 从七牛云删除失败", e);
        }
    }

    @Override
    public boolean supportType(AttachmentType type) {
        return AttachmentType.QINIUOSS.equals(type);
    }
}
