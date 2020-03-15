package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="查询团队list的参数")
public class SearchTeamParam {

    @ApiModelProperty("团队名")
    private String name;

    @ApiModelProperty("团队标签")
    private Integer tagId;

    /**
     * @see run.ut.app.model.enums.TeamsStatusEnum
     */
    @ApiModelProperty(value = "招人状态 0-不招人 1-招募中")
    private Integer status;
}
