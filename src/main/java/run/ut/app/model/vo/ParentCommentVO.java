package run.ut.app.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.PostComments;
import run.ut.app.model.dto.base.OutputConverter;

import java.util.List;

/**
 * @author wenjie
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ParentCommentVO extends BaseVO implements OutputConverter<ParentCommentVO, PostComments> {

    @ApiModelProperty(value = "评论id")
    private Long id;

    @ApiModelProperty(value = "所属帖子id")
    private Long postId;

    @ApiModelProperty(value = "评论者uid")
    private Long fromUid;

    @ApiModelProperty(value = "评论者昵称")
    private String nickname;

    @ApiModelProperty(value = "评论者头像")
    private String avatar;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "点赞数")
    private Long likes;

    @ApiModelProperty(value = "是否点赞过")
    private boolean isLike;

    @ApiModelProperty(value = "评论底下的回复")
    private List<ChildCommentVO> childComments;
}
