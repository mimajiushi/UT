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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoParam implements InputConverter<UserInfo> {

    @NotBlank
    private Long uid;

    private Integer schoolId;

    private Integer degreeId;

    private Integer areaId;

    private Integer role;

    @ApiModelProperty(value = "If he is a grade 2017 student, fill in 2017, Non student fill in 0")
    private Integer grade;

    @ApiModelProperty(value = "For example: Information Engineering, software engineering and other disciplines")
    private String subject;

    private String readName;
}
