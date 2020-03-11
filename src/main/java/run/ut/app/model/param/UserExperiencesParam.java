package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.UserExperiences;
import run.ut.app.model.dto.BaseDTO;
import run.ut.app.model.dto.base.InputConverter;
import run.ut.app.model.dto.base.OutputConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * UserExperiences
 * </p>
 *
 * @author wenjie
 * @since 2020-03-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UserExperiencesParam对象", description="")
public class UserExperiencesParam extends BaseDTO implements InputConverter<UserExperiences> {

    @ApiModelProperty(value = "此id更新时必须传入")
    private Long id;

    @ApiModelProperty(value = "不需要传这个字段")
    private Long uid;

    @ApiModelProperty(value = "项目或经历的名称", required = true)
    @NotBlank(message = "name must not be blank")
    @Size(max = 10, message = "Length of name must not be more than {max}")
    private String name;

    @ApiModelProperty(value = "获得过的奖项")
    @Size(max = 50, message = "Length of awards must not be more than {max}")
    private String awards;

    @ApiModelProperty(value = "在项目中担任的角色", required = true)
    @NotBlank(message = "role must not be blank")
    @Size(max = 10, message = "Length of role must not be more than {max}")
    private String role;

    @ApiModelProperty(value = "可以是github项目链接或者线上链接")
    @Size(max = 100, message = "Length of projectUrl must not be more than {max}")
    private String projectUrl;

    @ApiModelProperty(value = "对项目的描述", required = true)
    @NotBlank(message = "description must not be null")
    @Size(max = 1200, message = "Length of description must not be more than {max}")
    private String description;

    @ApiModelProperty(value = "开始时间")
    @NotBlank(message = "startTime must not be null")
    @Size(max = 15, message = "Length of startTime must not be more than {max}")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    @NotBlank(message = "endTime must not be null")
    @Size(max = 15, message = "Length of endTime must not be more than {max}")
    private String endTime;
}
