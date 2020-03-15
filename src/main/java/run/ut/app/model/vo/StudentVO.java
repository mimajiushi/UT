package run.ut.app.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.UserExperiencesDTO;
import run.ut.app.model.enums.DegreeEnum;

import java.util.List;

/**
 * studentVO
 * @author wenjie
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="StudentVO对象", description="")
public class StudentVO {

    @ApiModelProperty("uid")
    private Long uid;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("学历，如：大专/本科/博士及以上")
    private DegreeEnum degree;

    @ApiModelProperty("年级，如2017级就返回2017")
    private Integer grade;

    @ApiModelProperty("所学专业")
    private String subject;

    @ApiModelProperty("用户标签")
    private List<TagsDTO> tags;

    private String tagIds;

    private Integer schoolId;

    @ApiModelProperty("学校名称")
    private String school;

    @ApiModelProperty(value = "经历", notes = "用户个人主页显示")
    private List<UserExperiencesDTO> experiences;

    @ApiModelProperty("用户自我描述")
    private String description;

    @ApiModelProperty(value = "证件照正面")
    private String credentialsPhotoFront;

    @ApiModelProperty(value = "证件照反面")
    private String credentialsPhotoReverse;



}
