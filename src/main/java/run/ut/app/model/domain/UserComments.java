package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * UserComments
 * </p>
 *
 * @author wenjie
 * @since 2020-05-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserComments 对象", description = "")
public class UserComments extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long uid;

    private Long postId;

}
