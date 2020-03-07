package run.ut.app.config.thirdapi;

import lombok.Data;

/**
 * Baidu face API configuration
 *
 * @author wenjie
 */
@Data
public class BaiduFaceConfig {
    private String appId;
    private String appKey;
    private String secretKey;
    private int connTimeout;
    private int socketTimeOut;
    private int acceptScore;
    private String groupId;
}
