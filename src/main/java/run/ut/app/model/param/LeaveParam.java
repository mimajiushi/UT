package run.ut.app.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * leave param
 *
 * @author wenjie
 */
@Data
public class LeaveParam {

    @ApiModelProperty("1-队长开除成员 2-成员主动离开")
    @Min(1)
    @Max(2)
    private Integer mode;

    @ApiModelProperty("队伍id")
    @NotNull(message = "teamsId must not be null")
    private Long teamsId;

    @ApiModelProperty(value = "用户uid(操作者）", notes = "该字段由后端填充")
    private Long uid;

    @ApiModelProperty(value = "被开除者uid")
    private Long firedUid;
}
