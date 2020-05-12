package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * CommentPhotos
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
@ApiModel(value = "CommentPhotos对象", description = "1级评论带的图片")
public class CommentPhotos extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long commentId;

    @ApiModelProperty(value = "图片url")
    private String photo;

}
