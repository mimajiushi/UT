package run.ut.app.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.UserExperiencesDTO;

import java.util.List;

/**
 * studentVO
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="StudentVO对象", description="")
public class StudentVO {

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("学历，如：大专/本科/博士及以上")
    private String degree;

    @ApiModelProperty("年级，如2017级就返回2017")
    private Integer grade;

    @ApiModelProperty("用户标签")
    private List<TagsDTO> tags;

    @ApiModelProperty("学校名称")
    private String school;

    @ApiModelProperty("经历")
    private List<UserExperiencesDTO> experiences;

}
