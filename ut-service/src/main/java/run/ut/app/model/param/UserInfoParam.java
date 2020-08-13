package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.base.InputConverter;
import run.ut.app.model.enums.UserInfoStatusEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * UserInfoParam
 * </p>
 *
 * @author wenjie
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UserInfoParam对象", description = "")
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoParam extends BaseParam implements InputConverter<UserInfo> {

    private Long uid;

    @NotNull(message = "必须选择学校")
    private Integer schoolId;

    @NotNull(message = "不许选择学位")
    private Integer degreeId;

    @NotNull(message = "必须选择省份")
    private Integer areaId;

    /**
     * @see run.ut.app.model.enums.UserRolesEnum
     */
    @NotNull(message = "必须选择角色")
    private Integer role;

    @ApiModelProperty(value = "If he is a grade 2017 student, fill in 2017, Non student fill in 0")
    @NotNull(message = "必须选择年级")
    private Integer grade;

    @ApiModelProperty(value = "For example: Information Engineering, software engineering and other disciplines")
    @NotBlank(message = "必须填写专业")
    private String subject;

    @NotBlank(message = "必须填写真实姓名")
    private String realName;

    /**
     * @see UserInfoStatusEnum
     */
    @ApiModelProperty(value = "0-审核中 1-审核通过 -1-审核不通过", required = false)
    private Integer status;

    @ApiModelProperty(value = "正面照base64编码")
    @NotBlank(message = "正面照不能为空")
    private String credentialsPhotoFront;

    @ApiModelProperty(value = "反面照base64编码")
    @NotBlank(message = "反面照不能为空")
    private String credentialsPhotoReverse;
}
