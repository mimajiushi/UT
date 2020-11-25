package run.ut.app.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wenjie
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ChildCommentVO extends BaseVO implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "评论id")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "所属帖子id")
    private Long postId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "父评论id")
    private Long parentCommentId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "评论者uid")
    private Long fromUid;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "被评论/回复者uid")
    private Long toUid;

    @ApiModelProperty(value = "评论者昵称")
    private String fromNickname;

    @ApiModelProperty(value = "被评论/回复者昵称")
    private String toNickname;

    @ApiModelProperty(value = "评论者头像")
    private String fromAvatar;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "点赞数")
    private Long likes;

    @ApiModelProperty(value = "是否点赞过")
    private boolean isLike;
}
