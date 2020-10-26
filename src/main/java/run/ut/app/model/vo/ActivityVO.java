package run.ut.app.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.Activity;
import run.ut.app.model.dto.base.OutputConverter;

import java.time.LocalDateTime;

/**
 * @author Lucien
 * @version 1.0
 * @date 2020/5/12 21:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityVO extends BaseVO implements OutputConverter<ActivityVO, Activity> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "活动标题")
    private String title;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "活动内容")
    private String content;

    @ApiModelProperty(value = "预约人数")
    private int appointmentCount;

    @ApiModelProperty(value = "是否已预约")
    private boolean appointment;

    @ApiModelProperty(value = "是否已收藏")
    private boolean collect;

    @ApiModelProperty(value = "阅读数")
    @JsonSerialize(using = ToStringSerializer.class)
    private long readCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    private Integer deleted;
}
