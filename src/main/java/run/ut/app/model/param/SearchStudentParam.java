package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author wenjie
 */

@Data
@ApiModel(description = "查询学生list的参数")
public class SearchStudentParam {

    private Integer grade;

    private Integer tagId;

    private Integer schoolId;

    @ApiModelProperty("1-大专 2-本科 3-博士及以上")
    private Integer degreeId;

    @ApiModelProperty("昵称或真实姓名")
    @Size(max = 20, message = "昵称/姓名不得超过20字")
    private String name;

}
