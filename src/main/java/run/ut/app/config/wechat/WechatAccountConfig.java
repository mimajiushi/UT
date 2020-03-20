package run.ut.app.config.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author wenjie
 */

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    /**
     * 公众平台id
     */
    private String mpAppId;

    /**
     * 公众平台密钥
     */
    private String mpAppSecret;

    /**
     * 微信中获取用户openId 和 session_key的url
     */
    public String authorizeUrl;

    /**
     * 微信文档要求的type，只要是小程序都不需要修改
     */
    public String grantType;
}
