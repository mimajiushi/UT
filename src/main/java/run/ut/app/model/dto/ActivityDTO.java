package run.ut.app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ActivityDTO  extends BaseDTO implements OutputConverter<ActivityDTO, Activity> {

    private Long id;

    @ApiModelProperty(value = "活动标题")
    private String title;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "活动内容")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
}
