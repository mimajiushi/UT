package run.ut.app.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.BaseEntity;

/**
 * <p>
 * TeamsRecruitmentsTagsDTO
 * </p>
 *
 * @author wenjie
 * @since 2020-03-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="TeamsRecruitmentsTagsDTO 对象", description="")
@Deprecated
public class TeamsRecruitmentsTagsDTO extends BaseDTO {

    private Long id;

    private Long teamRecruitmentId;

    private Integer tagId;

}
