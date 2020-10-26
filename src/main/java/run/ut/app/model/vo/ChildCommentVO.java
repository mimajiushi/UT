package run.ut.app.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wenjie
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ChildCommentVO extends BaseVO {

    @ApiModelProperty(value = "评论id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "所属帖子id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long postId;

    @ApiModelProperty(value = "父评论id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentCommentId;

    @ApiModelProperty(value = "评论者uid")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fromUid;

    @ApiModelProperty(value = "被评论/回复者uid")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long toUid;

    @ApiModelProperty(value = "评论者昵称")
    private String fromNickname;

    @ApiModelProperty(value = "被评论/回复者昵称")
    private String toNickname;

    @ApiModelProperty(value = "评论者头像")
    private String fromAvatar;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "点赞数")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long likes;

    @ApiModelProperty(value = "是否点赞过")
    private boolean isLike;
}
