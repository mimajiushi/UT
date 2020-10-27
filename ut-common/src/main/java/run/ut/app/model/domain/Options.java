package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenjie
 * @since 2020-03-10
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Options对象", description = "")
public class Options extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String optionKey;

    private String optionValue;

    private Integer type;

    private String remark;
}
