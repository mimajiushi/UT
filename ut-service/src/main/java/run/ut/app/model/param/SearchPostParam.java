package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wenjie
 */

@Data
@ApiModel(description = "查询帖子list的参数")
@Accessors(chain = true)
public class SearchPostParam {

    @ApiModelProperty(value = "帖子标题")
    private String title;

    @ApiModelProperty(value = "帖子发布者uid")
    private Long uid;

    @ApiModelProperty(value = "版块id")
    private Long forumId;

    @ApiModelProperty(value = "查询（操作）者的uid", notes = "由后端填充、需要用户token")
    private Long operatorUid;
}
