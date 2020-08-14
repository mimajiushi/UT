package run.ut.app.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import run.ut.app.model.enums.TeamsMemberEnum;

import java.io.Serializable;

/**
 * TeamMemberVO
 * @author wenjie
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TeamMemberVO 对象", description = "")
public class TeamMemberVO implements Serializable {

    private Long id;

    private Long uid;

    private String nickname;

    private String avatar;

    private TeamsMemberEnum role;
}
