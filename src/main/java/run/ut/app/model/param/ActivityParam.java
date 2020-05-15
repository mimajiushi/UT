package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Lucien
 * @version 1.0
 * @date 2020/5/13 11:18
 */

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ActivityParam 对象", description = "")
public class ActivityParam implements Serializable {
    private Long id;

    @ApiModelProperty(value = "活动标题")
    @NotBlank(message = "活动标题不能为空")
    private String title;

    @ApiModelProperty(value = "封面图")
    @NotBlank(message = "封面图不能为空")
    private String cover;

    @ApiModelProperty(value = "活动内容")
    @Size(max = 10000, message = "活动内容不能超过 {max} 个字符（包含可能的 富文本 or MarkDown）")
    private String content;

    @ApiModelProperty(value = "活动开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "活动结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
