package run.ut.app.model.enums;

import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Options Enum.
 */
@Getter
public enum OptionsEnum implements Serializable {

    EMAIL_OPTIONS(
        "email_host",
        "email_username",
        "email_password",
        "email_from_name",
        "email_ssl_port",
        "email_enabled",
        "email_protocol"
    ),
    OSS_QNYUN_OPTIONS(
        "attachment_type",
        "oss_qiniu_domain_protocol",
        "oss_qiniu_zone",
        "oss_qiniu_domain",
        "oss_qiniu_access_key",
        "oss_qiniu_source",
        "oss_qiniu_bucket"
    ),
    WECHAT_MP(
        "mp-app-id",
        "mp-app-secret",
        "authorize-url",
        "grant-type"
    );

    OptionsEnum(String...keys) {
        this.keysList = Arrays.asList(keys);
    }

    private List<String> keysList;
}
