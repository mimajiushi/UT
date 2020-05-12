package run.ut.app.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.CommentPhotos;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * <p>
 * CommentPhotosDTO
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
@ApiModel(value = "CommentPhotosDTO 对象", description = "1级评论带的图片")
public class CommentPhotosDTO extends BaseDTO implements OutputConverter<CommentPhotosDTO, CommentPhotos> {

    private Long id;

    private Long commentId;

    @ApiModelProperty(value = "图片url")
    private String photo;

}
