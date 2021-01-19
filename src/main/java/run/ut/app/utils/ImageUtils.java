package run.ut.app.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author ryanwang
 * @author wenjie
 * @date 2019-12-10
 */
@Slf4j
public class ImageUtils {

    public static final String EXTENSION_ICO = "ico";

    public static boolean isImage(MultipartFile... file) {
        for (MultipartFile multipartFile : file) {
            String mimetype = multipartFile.getContentType();
            String type = mimetype.split("/")[0];
            if (!type.equals("image")) {
                return false;
            }
        }
        return true;
    }
}
