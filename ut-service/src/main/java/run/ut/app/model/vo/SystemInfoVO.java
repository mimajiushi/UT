package run.ut.app.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * SystemInfoVO
 * @author Lucien
 * @version 1.0
 * @date 2020/5/13 17:03
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SystemInfoVO对象", description = "")
public class SystemInfoVO {

    @ApiModelProperty("Java版本")
    private String javaVersion;

    @ApiModelProperty("JVM名称")
    private String jvmName;

    @ApiModelProperty("系统架构")
    private String osArch;

    @ApiModelProperty("系统版本")
    private String osVersion;

    @ApiModelProperty("操作系统名称")
    private String osName;

    @ApiModelProperty("服务器版本")
    private String serverInfo;

    @ApiModelProperty("CPU核数")
    private String availableProcessors;

}
