package run.ut.app.model.dto;

import lombok.*;
import lombok.experimental.Accessors;

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
@Deprecated
public class TeamsRecruitmentsTagsDTO extends BaseDTO {

    private Long id;

    private Long teamRecruitmentId;

    private Integer tagId;

}
