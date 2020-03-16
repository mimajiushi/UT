package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.enums.ApplyModeEnum;
import run.ut.app.model.enums.ApplyStatusEnum;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * <p>
 * 
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
@ApiModel(value="UserTeamApplyLog对象", description="")
public class UserTeamApplyLog extends BaseEntity{

    @TableId(value = "id", type = IdType.AUTO)
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
