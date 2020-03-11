package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="查询学生list的参数")
public class SearchStudentParam {

    private Integer grade;

    private Integer tagId;

    private Integer schoolId;

    @ApiModelProperty("1-大专 2-本科 3-博士及以上")
    private Integer degreeId;

    // TODO 考虑要不要加上地域搜索，可是这么做需要改表，等以后需要再加上吧。


}
