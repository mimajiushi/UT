package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import run.ut.app.model.domain.User;
import run.ut.app.model.dto.base.InputConverter;

import javax.validation.constraints.Size;
import java.io.Serializable;

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
@ApiModel(description = "可修改参数: 昵称、描述、性别", value = "可被用户任意修改的基本信息参数")
public class UserSimpleParam implements InputConverter<User>, Serializable {

    @ApiModelProperty(value = "用户uid，前端不需要传，由后端解析token填充")
    private Long uid;

    @Size(max = 10, message = "用户昵称的字符长度不能超过 {max} 字")
    private String nickname;

    @Size(max = 2000, message = "用户描述的字符长度不能超过 {max} 字")
    private String description;

    private Integer sex;
}
