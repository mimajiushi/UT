package run.ut.app.config.thirdapi;

import com.baidu.aip.face.AipFace;
import com.github.qcloudsms.SmsSingleSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn(value = "thirdApiConfig")
public class AipFaceConfig {
    private final ThirdApiConfig thirdApiConfig;

    @Autowired
    public AipFaceConfig(ThirdApiConfig thirdApiConfig) {
        this.thirdApiConfig = thirdApiConfig;
    }

    @Bean
    public AipFace aipFace(){
        BaiduFaceConfig baidu = thirdApiConfig.getBaidu();
        AipFace aipFace = new AipFace(baidu.getAppId(), baidu.getAppKey(), baidu.getSecretKey());
        aipFace.setConnectionTimeoutInMillis(baidu.getConnTimeout());
        aipFace.setSocketTimeoutInMillis(baidu.getSocketTimeOut());
        return aipFace;
    }

    @Bean
    SmsSingleSender getSmsSingleSender(){
        SmsConfig smsConfig = thirdApiConfig.getSms();
        return new SmsSingleSender(smsConfig.getAppId(),smsConfig.getAppKey());
    }
}
