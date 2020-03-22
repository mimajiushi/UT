package run.ut.app.model.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import run.ut.app.model.domain.User;
import run.ut.app.model.dto.base.InputConverter;

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
@ApiModel(description="可被用户任意修改的基本信息参数")
public class UserSimpleParam implements InputConverter<User> {

    @ApiModelProperty(value = "用户uid，前端不需要传，由后端解析token填充")
    private Long uid;

    @Size(max = 50, message = "用户昵称的字符长度不能超过 {max}")
    private String nickname;

    @Size(max = 2000, message = "用户描述的字符长度不能超过 {max}")
    private String description;

    private Integer sex;
}
