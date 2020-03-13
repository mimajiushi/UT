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
@ApiModel(value="TeamsRecruitments对象", description="")
public class TeamsRecruitments extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long teamId;

    @ApiModelProperty(value = "招聘岗位名称")
    private String name;

    @ApiModelProperty(value = "岗位要求、描述")
    private String description;

}
