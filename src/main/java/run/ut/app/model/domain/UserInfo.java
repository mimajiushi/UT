package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.enums.DegreeEnum;
import run.ut.app.model.enums.UserInfoStatusEnum;

/**
 * <p>
 * UserInfo
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
@ApiModel(value = "UserInfo对象", description = "")
public class UserInfo extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long uid;

    private Integer schoolId;

    private DegreeEnum degreeId;

    private Integer areaId;

    @ApiModelProperty(value = "Role of user application")
    private Integer role;

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
