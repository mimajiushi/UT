package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.TeamsRecruitments;
import run.ut.app.model.dto.base.InputConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * TeamsRecruitmentsParam
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor@Builder
@AllArgsConstructor
@ApiModel(value = "TeamsRecruitmentsParam 对象", description = "")
public class TeamsRecruitmentsParam implements InputConverter<TeamsRecruitments> {

    @ApiModelProperty(value = "更新的时候传的岗位唯一id")
    private Long id;

    @ApiModelProperty(value = "这个参数不用传")
    private Long leaderId;

    @ApiModelProperty(value = "所属团队的id", required = true)
    private Long teamId;

    @ApiModelProperty(value = "招聘岗位名称", required = true)
    @NotBlank(message = "岗位名称不能为空")
    @Size(max = 15, message = "岗位名称不能超过{max}个字")
    private String name;

    @ApiModelProperty(value = "岗位要求、描述")
    @Size(max = 5000, message = "岗位描述不能超过 {max} 个字符（可能白喊的 富文本 or MarkDown等特殊字符）")
    private String description;
}
