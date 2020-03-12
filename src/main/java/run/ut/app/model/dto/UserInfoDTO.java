package run.ut.app.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.BaseEntity;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.base.OutputConverter;
import run.ut.app.model.enums.DegreeEnum;
import run.ut.app.model.enums.UserInfoStatusEnum;
import run.ut.app.model.enums.UserRolesEnum;

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
@ApiModel(value="UserInfoDTO对象", description="")
public class UserInfoDTO extends BaseDTO implements OutputConverter<UserInfoDTO, UserInfo> {

    private Long id;

    private Long uid;

    private String school;

    private DegreeEnum degreeId;

    private String area;

    @ApiModelProperty(value = "Role of user application")
    private UserRolesEnum role;

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

    private String tagIds;
}
