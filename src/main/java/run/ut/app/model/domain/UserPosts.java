package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * UserPosts
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
@ApiModel(value = "UserPosts映射对象", description = "")
public class UserPosts extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long uid;

    private Long postId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer deleted;

}
