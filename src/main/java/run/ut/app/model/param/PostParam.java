package run.ut.app.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import run.ut.app.model.domain.Posts;
import run.ut.app.model.dto.base.InputConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Post params
 * @author wenjie
 */
@Data
public class PostParam implements InputConverter<Posts> {

    private Long id;

    @ApiModelProperty(value = "用户uid，由后端填充")
    private Long uid;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空！")
    @Size(max = 15, message = "标题不能超过15字")
    private String title;

    @ApiModelProperty(value = "内容")
    @NotBlank(message = "帖子内容不能为空")
    @Size(max = 10000, message = "内容长度不能超过10000字")
    private String content;
}
