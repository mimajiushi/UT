package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "处理邀请/申请的请求参数")
public class DealInvitationOrApplyParam {

    @ApiModelProperty(value = "申请/邀请的id集合")
    @NotNull(message = "ids must not be null")
    private Long[] ids;

    @ApiModelProperty(value = "1-同意申请/邀请 -1-拒绝申请/邀请")
    @Min(value = -1)
    @Max(value = 1)
    private Integer status;

    @ApiModelProperty(value = "拒绝的理由")
    private String reason;
}
