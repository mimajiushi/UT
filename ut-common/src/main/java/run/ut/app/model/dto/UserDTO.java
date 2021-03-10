package run.ut.app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.User;
import run.ut.app.model.dto.base.OutputConverter;
import run.ut.app.model.enums.SexEnum;
import run.ut.app.security.token.AuthToken;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * UserDTO
 * </p>
 *
 * @author wenjie
 * @since 2020-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseDTO implements OutputConverter<UserDTO, User> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    private String openid;

    @JsonIgnore
    private String password;

    private String nickname;

    private String avatar;

    private String phoneNumber;

    private String email;

    private String description;

    private SexEnum sex;

    private Integer roles;

    private List<String> rolesName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    private AuthToken token;
}
