package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * TeamsParam
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TeamsParam 对象", description = "")
public class TeamsParam implements Serializable {

    @ApiModelProperty("团队id，更新时传入")
    private Long id;

    @NotBlank(message = "团队名不能为空")
    @Size(max = 10, message = "团队名不能超过{max}字")
    private String name;

    @ApiModelProperty(value = "团队的标签id")
    @Size(max = 3, message = "标签最多不能超过")
    private List<Long> tagIds;

    @ApiModelProperty(value = "团队描述，关于团队表，暂时不额外附加经历表了，经历也都写在这")
    @Size(max = 5000, message = "团队描述不能超过 {max} 个字符（包含可能的 富文本 or MarkDown）")
    private String description;

    @ApiModelProperty(value = "团队logo的url地址")
    private String logo;

    @ApiModelProperty(value = "0-不招人 1-招募中，只有招募中状态的团队才会展示在首页")
    @Min(value = -1, message = "必须设置发布状态")
    private Integer status;

}
