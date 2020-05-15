package run.ut.app.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.PostComments;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * <p>
 * PostCommentsDTO
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
@ApiModel(value = "PostCommentsDTO 对象", description = "")
public class PostCommentsDTO extends BaseDTO implements OutputConverter<PostCommentsDTO, PostComments> {

    private Long id;

    private Long postId;

    @ApiModelProperty(value = "父评论id，如果为0则表示没有父级")
    private Long parentCommentId;

    private Long toUid;

    private Long fromUid;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "点赞数")
    private Long likes;

}
