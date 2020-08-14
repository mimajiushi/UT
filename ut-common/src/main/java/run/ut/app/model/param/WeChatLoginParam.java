package run.ut.app.model.param;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value = "微信登录所需要带的参数")
public class WeChatLoginParam implements Serializable {

    /**
     * 微信小程序的授权码
     */
    @NotBlank(message = "微信小程序授权码不能为空")
    private String code;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;

    /**
     * 用户头像
     */
    @NotBlank(message = "用户头像url不能为空")
    private String avatarUrl;

}
