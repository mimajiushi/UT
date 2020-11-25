package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 *  tags
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
@ApiModel(value = "Tags", description = "")
public class Tags extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    @Deprecated
    private Integer parentId;

    private String name;

    @Deprecated
    private Integer level;
}
