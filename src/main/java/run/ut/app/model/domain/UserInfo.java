package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.enums.DegreeEnum;
import run.ut.app.model.enums.UserInfoStatusEnum;
import run.ut.app.model.enums.UserRolesEnum;

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
@ApiModel(value="UserInfo对象", description="")
public class UserInfo extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long uid;

    private Integer schoolId;

    private DegreeEnum degreeId;

    private Integer areaId;

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
