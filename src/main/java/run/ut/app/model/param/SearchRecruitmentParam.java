package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.Size;


/**
 * @author wenjie
 */
@Data
@ApiModel(description="查询职位list的参数")
public class SearchRecruitmentParam {

    @ApiModelProperty(value = "职位名称")
    @Size(max = 10, message = "查询名不能大于{max}字")
    private String name;

    @ApiModelProperty(value = "标签id")
    @Deprecated
    private Long tagId;


    @ApiModelProperty(value = "0-团队处于不招人状态 1-团队处于招人状态。",
            notes = "默认为1，前端不需要传，默认只展示招人状态的团队的岗位")
    private Integer status;

}
