package run.ut.app.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.UserTeamApplyLog;
import run.ut.app.model.dto.base.OutputConverter;

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

    @ApiModelProperty(value = "1-用户申请加入团队 2-团队邀请用户")
    private Integer mode;

    @ApiModelProperty(value = "0-待处理 1-申请通过 -1-申请被拒绝")
    private Integer status;

    @ApiModelProperty(value = "拒绝的原因")
    private String reason;

}
