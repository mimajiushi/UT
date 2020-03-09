package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.base.InputConverter;

import javax.validation.constraints.NotBlank;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoParam implements InputConverter<UserInfo> {

    @NotBlank(message = "用户uid不能为空")
    private Long uid;

    @NotBlank(message = "学校id不能为空")
    private Integer schoolId;

    @NotBlank(message = "学历id不能为空")
    private Integer degreeId;

    @NotBlank(message = "市id不能为空")
    private Integer areaId;

    @NotBlank(message = "认证角色不能为空")
    private Integer role;

    @ApiModelProperty(value = "If he is a grade 17 student, fill in 17")
    @NotBlank(message = "年级角色不能为空")
    private Integer grade;

    @ApiModelProperty(value = "For example: Information Engineering, software engineering and other disciplines")
    @NotBlank(message = "专业名称不能为空")
    private String subject;

    @NotBlank(message = "真实姓名不能为空")
    private String readName;

    @ApiModelProperty(value = "photo file")
    @NotBlank(message = "证件照正面不能为空")
    private MultipartFile credentialsPhotoFront;

    @ApiModelProperty(value = "photo file")
    @NotBlank(message = "证件照反面不能为空")
    private MultipartFile credentialsPhotoReverse;

}
