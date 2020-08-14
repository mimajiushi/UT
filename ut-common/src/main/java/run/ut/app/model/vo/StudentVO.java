package run.ut.app.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.UserExperiencesDTO;
import run.ut.app.model.enums.DegreeEnum;
import run.ut.app.model.enums.SexEnum;

import java.io.Serializable;
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
@ApiModel(value = "StudentVO对象", description = "")
public class StudentVO implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("uid")
    private Long uid;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别")
    private SexEnum sex;

    @ApiModelProperty(value = "角色id", notes = "需要通过与运算解析")
    private Integer roles;

    @ApiModelProperty(value = "用户绑定的手机号码", notes = "只有用户查看自己主页才会显示")
    private String phoneNumber;

    @ApiModelProperty(value = "用户绑定的邮箱")
    private String email;

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

    @ApiModelProperty(value = "有无认证信息")
    private boolean hasAuthInfo;

}
