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

/**
 * <p>
 * TeamsMembers
 * </p>
 *
 * @author wenjie
 * @since 2020-03-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="TeamsMembers对象", description="")
public class TeamsMembers extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long teamId;

    private Long uid;

    @ApiModelProperty(value = "0-队员 1-队长")
    private Integer isLeader;

}
