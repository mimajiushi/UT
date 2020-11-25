package run.ut.app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long postId;

    @ApiModelProperty(value = "父评论id，如果为0则表示没有父级")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentCommentId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long toUid;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long fromUid;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "点赞数")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long likes;

}
