package run.ut.app.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.PostComments;
import run.ut.app.model.dto.base.OutputConverter;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenjie
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ParentCommentVO extends BaseVO implements OutputConverter<ParentCommentVO, PostComments>, Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "评论id")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "所属帖子id")
    private Long postId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "帖子标题")
    private String title;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "评论者uid")
    private Long fromUid;

    @ApiModelProperty(value = "评论者昵称")
    private String nickname;

    @ApiModelProperty(value = "评论者头像")
    private String avatar;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "点赞数")
    private Long likes;

    @ApiModelProperty(value = "是否点赞过")
    private boolean isLike;

    @ApiModelProperty(value = "评论底下的回复")
    private List<ChildCommentVO> childComments;
}
