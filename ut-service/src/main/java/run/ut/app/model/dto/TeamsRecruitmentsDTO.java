package run.ut.app.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
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
public class TeamsRecruitmentsDTO extends BaseDTO implements OutputConverter<TeamsRecruitmentsDTO, TeamsRecruitments> {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long teamId;

    @ApiModelProperty(value = "招聘岗位名称")
    private String name;

    private String tagIds;

    @ApiModelProperty(value = "岗位要求、描述")
    private String description;

}
