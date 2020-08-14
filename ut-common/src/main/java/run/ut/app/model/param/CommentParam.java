package run.ut.app.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Comment param
 *
 * @author wenjie
 * @date 2020-5-14
 */

@Data
public class CommentParam {

    @ApiModelProperty(value = "帖子id")
    @NotNull(message = "post id must not be null.")
    private Long postId;

    @ApiModelProperty(value = "父评论id", notes = "回复帖子则默认为0，回复评论才需要")
    private Long parentCommentId = 0L;

    @ApiModelProperty(value = "被回复者uid", notes = "同上")
    private Long toUid;

    @ApiModelProperty(value = "评论/回复者uid", notes = "由后端填充")
    private Long fromUid;

    @ApiModelProperty(value = "评论内容")
    @NotBlank(message = "content must not be blank")
    private String content;
}
