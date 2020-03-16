package run.ut.app.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.UserTeamApplyLog;
import run.ut.app.model.dto.base.OutputConverter;
import run.ut.app.model.enums.ApplyModeEnum;
import run.ut.app.model.enums.ApplyStatusEnum;

/**
 * <p>
 * UserTeamApplyLogDTO
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UserTeamApplyLogDTO 对象", description="")
public class UserTeamApplyLogDTO extends BaseDTO implements OutputConverter<UserTeamApplyLogDTO, UserTeamApplyLog> {

    private Long id;

    private Long uid;

    private Long teamId;

    @ApiModelProperty(value = "申请的职位id", notes = "0-直接申请加入团队，> 0则是申请对应职位")
    private Long recruitmentId;

    @ApiModelProperty(value = "1-用户申请加入团队 2-团队邀请用户")
    private ApplyModeEnum mode;

    @ApiModelProperty(value = "0-待处理 1-申请通过 -1-申请被拒绝")
    private ApplyStatusEnum status;

    @ApiModelProperty(value = "拒绝的原因")
    private String reason;

}
