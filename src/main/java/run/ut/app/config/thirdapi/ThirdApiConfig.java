package run.ut.app.config.thirdapi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wenjie
 */
@Data
@Component("thirdApiConfig")
@ConfigurationProperties(prefix = "third-api-config")
public class ThirdApiConfig {
    private SmsConfig sms;
}
