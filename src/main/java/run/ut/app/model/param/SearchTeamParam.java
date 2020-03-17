package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author wenjie
 */

@Data
@ApiModel(description="查询团队list的参数")
public class SearchTeamParam {

    @ApiModelProperty("团队名")
    @Size(max = 10, message = "团队名不能超过{max}字")
    private String name;

    @ApiModelProperty("团队标签")
    private Integer tagId;

    /**
     * @see run.ut.app.model.enums.TeamsStatusEnum
     */
    @ApiModelProperty(value = "招人状态 0-不招人 1-招募中")
    private Integer status;
}
