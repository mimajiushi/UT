package run.ut.app.model.support;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.MediaType;

/**
 * Upload result dto.
 *
 * @author wenjie
 */
@Data
@ToString
public class UploadResult {

    private String filename;

    private String filePath;

    private String key;

    private String thumbPath;

    private String suffix;

    private MediaType mediaType;

    private Long size;

    private Integer width;

    private Integer height;

}
