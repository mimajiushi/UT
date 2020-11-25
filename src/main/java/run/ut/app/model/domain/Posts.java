package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.dto.base.InputConverter;
import run.ut.app.model.param.PostParam;

/**
 * <p>
 * Posts
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Posts对象", description = "")
public class Posts extends BaseEntity implements InputConverter<PostParam> {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long uid;

    @ApiModelProperty(value = "版块id")
    private Long forumId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "点赞数")
    private Long likes;
}
