package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import run.ut.app.model.enums.PostQuerySortEnum;

import java.io.Serializable;

/**
 * @author wenjie
 */

@Data
@ApiModel(description = "查询帖子list的参数")
@Accessors(chain = true)
public class SearchPostParam implements Serializable {

    @ApiModelProperty(value = "帖子标题")
    private String title;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "帖子发布者uid")
    private Long uid;

    @ApiModelProperty(value = "版块id")
    private Long forumId;

    @ApiModelProperty(value = "查询（操作）者的uid", notes = "由后端填充、需要用户token")
    private Long operatorUid;

    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "页大小")
    private Integer pageSize = 15;

    /**
     * @see PostQuerySortEnum
     */
    @ApiModelProperty(value = "排序字段")
    private Integer sortType = 2;

    @ApiModelProperty(value = "true为倒序")
    private Boolean desc = true;


}
