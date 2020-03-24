package run.ut.app.model.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.base.InputConverter;
import run.ut.app.model.enums.UserInfoStatusEnum;

import javax.annotation.Nullable;
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
@ApiModel(value="UserInfoParam对象", description="")
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
    @NotBlank()
    private String credentialsPhotoFront;

    @ApiModelProperty(value = "反面照base64编码")
    private String credentialsPhotoReverse;
}
