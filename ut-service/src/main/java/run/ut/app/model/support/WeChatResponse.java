package run.ut.app.model.support;

import lombok.Data;

/**
 * 微信小程序授权响应对象
 *
 * @author wenjie
 */
@Data
public class WeChatResponse {
    /**
     * 用户唯一标识
     */
    private String openid;

    /**
     * 会话密钥
     */
    private String session_key;

    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见: https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/union-id.html。
     */
    private String unionid;

    /**
     * 错误码
     */
    private Long errcode;

    /**
     * 错误信息
     */
    private String errmsg;


}
