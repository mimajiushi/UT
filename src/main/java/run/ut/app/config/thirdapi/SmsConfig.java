package run.ut.app.config.thirdapi;

import com.github.qcloudsms.SmsSingleSender;
import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 * Tencent SMS configuration
 *
 * @author wenjie
 */

@Data
public class SmsConfig{
    private Integer appId;
    private String appKey;
    private Integer templateId;
    private String sign;
    private String intervalMin;
    private String validMin;
}
