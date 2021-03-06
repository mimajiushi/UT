package run.ut.app.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.base.OutputConverter;
import run.ut.app.model.enums.DegreeEnum;
import run.ut.app.model.enums.UserInfoStatusEnum;

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

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    @JsonIgnore
    private Integer schoolId;

    private String school;

    private DegreeEnum degreeId;

    private String area;

    @ApiModelProperty(value = "Role of user application")
    @JsonIgnore
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
