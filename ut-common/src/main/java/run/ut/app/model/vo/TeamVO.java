package run.ut.app.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.Teams;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsRecruitmentsDTO;
import run.ut.app.model.dto.base.OutputConverter;
import run.ut.app.model.enums.TeamsStatusEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * TeamVO
 * @author wenjie
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TeamVO 对象", description = "")
public class TeamVO implements OutputConverter<TeamVO, Teams>, Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("团队id")
    private Long id;

    @ApiModelProperty("团队名")
    private String name;

    @ApiModelProperty("团队logo")
    private String logo;

    @ApiModelProperty("团队描述，建议前端只截取一小段")
    private String description;

    @ApiModelProperty("团队标签")
    private List<TagsDTO> tags;

    private String tagIds;

    @ApiModelProperty("团队状态")
    private TeamsStatusEnum status;

    @ApiModelProperty("团队招聘的职位")
    private List<TeamsRecruitmentsDTO> recruitments;

    @ApiModelProperty("团队成员简要信息")
    private List<TeamMemberVO> members;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime createTime;


}
