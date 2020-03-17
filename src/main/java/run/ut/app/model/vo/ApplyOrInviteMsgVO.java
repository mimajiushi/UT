package run.ut.app.model.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import run.ut.app.model.enums.ApplyStatusEnum;

import java.time.LocalDateTime;

/**
 * @author wenjie
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="ApplyOrInviteMsgVO 对象", description="用户收到的邀请信息 or 团队收到的申请信息")
public class ApplyOrInviteMsgVO {

    @ApiModelProperty(value = "申请/邀请的id")
    private Long id;

    @ApiModelProperty(value = "申请/邀请用户的uid",
            notes = "若需要查看用户的详细信息，前端可以根据uid去请求用户详情信息的接口")
    private Long uid;

    @ApiModelProperty(value = "申请/邀请用户的昵称")
    private String nickname;

    @ApiModelProperty(value = "申请/邀请的团队id", notes = "若需要查看团队的详细信息，也可以通过此id请求对应的接口")
    private Long teamId;

    @ApiModelProperty(value = "申请团队的团队名")
    private String teamName;

    @ApiModelProperty(value = "申请/邀请的职位id", notes = "若需要查看职位详情，可以通过此id请求对应接口")
    private Long recruitmentId;

    @ApiModelProperty(value = "申请/邀请的职位名")
    private String recruitmentName;

    @ApiModelProperty(value = "当前处理状态")
    private ApplyStatusEnum status;

    @ApiModelProperty(value = "如果这条申请/邀请被拒绝了，可能带有拒绝原因")
    private String reason;

    @ApiModelProperty(value = "申请/邀请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
