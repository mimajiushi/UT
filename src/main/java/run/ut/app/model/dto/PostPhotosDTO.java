package run.ut.app.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.PostComments;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * <p>
 * PostPhotosDTO
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
@ApiModel(value = "PostPhotosDTO 对象", description = "")
public class PostPhotosDTO extends BaseDTO implements OutputConverter<PostPhotosDTO, PostComments> {

    private Long id;

    @ApiModelProperty(value = "帖子id")
    private Long postId;

    @ApiModelProperty(value = "图片url")
    private String photo;
}
