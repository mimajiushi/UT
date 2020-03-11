package run.ut.app.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.BaseEntity;

import java.time.LocalDateTime;

/**
 * <p>
 * UserTags
 * </p>
 *
 * @author wenjie
 * @since 2020-03-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UserTags对象", description="")
public class UserTagsDTO extends BaseDTO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long uid;

    private Integer tagId;

}
