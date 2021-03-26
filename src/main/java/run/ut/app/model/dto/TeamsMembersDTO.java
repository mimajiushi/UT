package run.ut.app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.TeamsMembers;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * <p>
 * TeamsMembersDTO
 * </p>
 *
 * @author wenjie
 * @since 2020-03-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamsMembersDTO extends BaseDTO implements OutputConverter<TeamsMembersDTO, TeamsMembers> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long teamId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long recruitmentId;

    @ApiModelProperty(value = "0-队员 1-队长")
    private Integer isLeader;

}
