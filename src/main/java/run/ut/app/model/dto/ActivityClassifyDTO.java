package run.ut.app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.ActivityClassify;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * <p>
 * 活动分类
 * </p>
 *
 * @author chenwenjie.star
 * @since 2021-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityClassifyDTO extends BaseDTO implements OutputConverter<ActivityClassifyDTO, ActivityClassify> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 分类名
     */
    private String cname;
}
