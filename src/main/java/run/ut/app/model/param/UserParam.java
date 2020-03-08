package run.ut.app.model.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import run.ut.app.model.domain.User;
import run.ut.app.model.dto.base.InputConverter;
import run.ut.app.model.support.CreateCheck;
import run.ut.app.model.support.UpdateCheck;

import javax.validation.constraints.Email;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * <p>
 * UserParam
 * </p>
 *
 * @author wenjie
 * @since 2020-03-08
 */
@Data
@ToString
@ApiModel(description="用户注册 or 登录参数")
public class UserParam implements InputConverter<User> {

    @ApiModelProperty(value = "微信openid")
    private String openid;

    @ApiModelProperty(value = "微信授权码")
    private String weChatCode;

    @ApiModelProperty(value = "短信授权码")
    private String smsCode;

    @Null(groups = UpdateCheck.class)
    @Size(min = 8, max = 100, message = "密码的字符长度必须在 {min} - {max} 之间")
    private String password;

    @Size(max = 11, message = "手机号码的字符长度不能超过 {max}")
    private String phoneNumber;

    @Email(message = "电子邮件地址的格式不正确")
    @Size(max = 127, message = "电子邮件的字符长度不能超过 {max}")
    private String email;

    @Size(max = 50, message = "用户描述的字符长度不能超过 {max}")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
