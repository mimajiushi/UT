package run.ut.app.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.Posts;
import run.ut.app.model.dto.base.InputConverter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Post VO
 * @author wenjie
 */
@Data
@Accessors(chain = true)
public class PostVO implements InputConverter<Posts>, Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "帖子id")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "发帖用户的uid")
    private Long uid;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "所属版块id")
    private Long forumId;

    @ApiModelProperty(value = "所属版块")
    private String forumName;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容（截取一定字数）")
    private String content;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "点赞数")
    private Long likeCount;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "阅读数")
    private Long readCount;

    @ApiModelProperty(value = "是否已收藏", notes = "打开帖子的时候才加载")
    private boolean isCollect;

    @ApiModelProperty(value = "是否已点赞")
    private boolean isLike;

    @ApiModelProperty(value = "帖子创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "帖子更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    private Integer deleted;

}
