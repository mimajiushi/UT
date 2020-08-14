package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.enums.SexEnum;

/**
 * <p>
 *
 * </p>
 *
 * @author wenjie
 * @since 2020-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "User对象", description = "")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    private String openid;

    private String password;

    private String nickname;

    private SexEnum sex;

    private Integer roles;

    private String avatar;

    private String phoneNumber;

    private String email;

    private String description;

}
