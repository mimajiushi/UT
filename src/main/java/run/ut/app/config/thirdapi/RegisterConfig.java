package run.ut.app.config.thirdapi;

import com.github.qcloudsms.SmsSingleSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn(value = "thirdApiConfig")
public class RegisterConfig {
    private final ThirdApiConfig thirdApiConfig;

    @Autowired
    public RegisterConfig(ThirdApiConfig thirdApiConfig) {
        this.thirdApiConfig = thirdApiConfig;
    }

    @Bean
    SmsSingleSender getSmsSingleSender(){
        SmsConfig smsConfig = thirdApiConfig.getSms();
        return new SmsSingleSender(smsConfig.getAppId(),smsConfig.getAppKey());
    }
}
