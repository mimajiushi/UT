package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * PostComments
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
@ApiModel(value = "PostComments对象", description = "")
public class PostComments extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long postId;

    @ApiModelProperty(value = "父评论id，如果为0则表示没有父级")
    private Long fatherCommentId;

    private Long toUid;

    private Long fromUid;

    @ApiModelProperty(value = "被回复者的昵称（冗余）")
    private String toNickname;

    @ApiModelProperty(value = "回复者昵称")
    private String fromNickname;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "点赞数")
    private Long likes;

}
