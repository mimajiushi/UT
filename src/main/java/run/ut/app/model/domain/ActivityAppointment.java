package run.ut.app.model.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
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
@ApiModel(value = "活动预约对象", description = "")
public class ActivityAppointment extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long uid;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long activityId;

}
