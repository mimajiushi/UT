package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * UserExperiences
 * </p>
 *
 * @author wenjie
 * @since 2020-03-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserExperiences对象", description = "")
public class UserExperiences extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long uid;

    private String name;

    @ApiModelProperty(value = "Just fill in the award")
    private String awards;

    @ApiModelProperty(value = "role in the project")
    private String role;

    @ApiModelProperty(value = "It can be GitHub or online URL and so on")
    private String projectUrl;

    private String description;

    private String startTime;

    private String endTime;
}
