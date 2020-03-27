package run.ut.app.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.TeamsRecruitments;
import run.ut.app.model.dto.BaseDTO;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.base.InputConverter;
import run.ut.app.model.dto.base.OutputConverter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * TeamsRecruitmentsVO
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="TeamsRecruitmentsVO 对象", description="")
public class TeamsRecruitmentsVO implements OutputConverter<TeamsRecruitmentsVO, TeamsRecruitments> {

    @ApiModelProperty("职位id")
    private Long id;

    @ApiModelProperty("职位所属团队id")
    private Long teamId;

    @ApiModelProperty(value = "招聘岗位名称")
    private String name;

//    private String tagIds;
//
//    @ApiModelProperty(value = "招聘岗位的标签")
//    private List<TagsDTO> tags;

    @ApiModelProperty(value = "岗位要求、描述")
    private String description;

    @ApiModelProperty(value = "只注入了所属团队的名字、头像等，若查看详情请前端根据团队id跳转到团队详情的接口",
            notes = "在首页显示职位list的接口则不会填充这个参数")
    private TeamVO teamVO;

    @ApiModelProperty(value = "队伍名" ,notes = "首页显示职位list时就会填充这个参数")
    private String teamName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
