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

    @NotBlank
    private Long uid;

    private Integer schoolId;

    private Integer degreeId;

    @Deprecated
    private Integer areaId;

    /**
     * @see run.ut.app.model.enums.UserRolesEnum
     */
    private Integer role;

    @ApiModelProperty(value = "If he is a grade 2017 student, fill in 2017, Non student fill in 0")
    private Integer grade;

    @ApiModelProperty(value = "For example: Information Engineering, software engineering and other disciplines")
    private String subject;

    private String realName;

    /**
     * @see UserInfoStatusEnum
     */
    @ApiModelProperty(value = "0-审核中 1-审核通过 -1-审核不通过")
    private Integer status;
}
