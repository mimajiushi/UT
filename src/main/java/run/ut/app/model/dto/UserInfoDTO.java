package run.ut.app.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.base.OutputConverter;
import run.ut.app.model.enums.DegreeEnum;
import run.ut.app.model.enums.UserInfoStatusEnum;
import run.ut.app.model.enums.UserRolesEnum;

import java.util.List;

/**
 * <p>
 * UserInfoDTO
 *
 * </p>
 *
 * @author wenjie
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO extends BaseDTO implements OutputConverter<UserInfoDTO, UserInfo> {

    private Long id;

    private Long uid;

    private String school;

    private DegreeEnum degreeId;

    private String area;

    @ApiModelProperty(value = "Role of user application")
    private Integer role;

    private List<String> roles;

    @ApiModelProperty(value = "If he is a grade 17 student, fill in 17")
    private Integer grade;

    @ApiModelProperty(value = "For example: Information Engineering, software engineering and other disciplines")
    private String subject;

    private String realName;

    @ApiModelProperty(value = "photo url")
    private String credentialsPhotoFront;

    @ApiModelProperty(value = "photo url")
    private String credentialsPhotoReverse;

    private UserInfoStatusEnum status;

    private String reason;

    private String tagIds;
}
