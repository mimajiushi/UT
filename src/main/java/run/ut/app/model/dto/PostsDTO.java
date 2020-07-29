package run.ut.app.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.Posts;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * <p>
 * PostsDTO
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
@ApiModel(value = "PostsDTO 对象", description = "")
public class PostsDTO extends BaseDTO implements OutputConverter<PostsDTO, Posts> {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long uid;

    @ApiModelProperty(value = "版块id")
    private Long forumId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "点赞数")
    private Long likes;
}
