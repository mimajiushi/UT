package run.ut.app.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.ActivityCollect;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * @author Lucien
 * @version 1.0
 * @date 2020/5/12 21:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCollectDTO extends BaseDTO implements OutputConverter<ActivityCollectDTO, ActivityCollect> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long uid;

    private Long activityId;
}