package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.UserTeamApplyLog;
import run.ut.app.model.dto.BaseDTO;
import run.ut.app.model.dto.base.InputConverter;
import run.ut.app.model.dto.base.OutputConverter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * UserTeamApplyLogDTO
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UserTeamApplyLogDTO 对象", description="")
public class TeamApplyOrInviteParam implements InputConverter<UserTeamApplyLog> {

    @ApiModelProperty(value = "可能会在某些更新情景下使用")
    private Long id;

    @ApiModelProperty(value = "团队邀请用户加入时前端才需要传，用户申请加入团队时由后端自动填充")
    private Long uid;

    @ApiModelProperty(value = "团队id")
    @NotNull(message = "队伍id不能为空")
    private Long teamId;

    @ApiModelProperty(value = "申请的职位id", notes = "0-直接申请/邀请加入团队，> 0则是申请/邀请对应职位")
    @Min(value = 0)
    private Long recruitmentId;

}
