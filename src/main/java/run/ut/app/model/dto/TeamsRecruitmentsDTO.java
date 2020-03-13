package run.ut.app.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.BaseEntity;
import run.ut.app.model.domain.TeamsRecruitments;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * <p>
 * TeamsRecruitmentsDTO
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
@ApiModel(value="TeamsRecruitmentsDTO 对象", description="")
public class TeamsRecruitmentsDTO extends BaseDTO implements OutputConverter<TeamsRecruitmentsDTO, TeamsRecruitments> {

    private Long id;

    private Long teamId;

    @ApiModelProperty(value = "招聘岗位名称")
    private String name;

    @ApiModelProperty(value = "岗位要求、描述")
    private String description;

}
