package run.ut.app.model.dto;

import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.UserExperiences;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * <p>
 * UserExperiences
 * </p>
 *
 * @author wenjie
 * @since 2020-03-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserExperiencesDTO extends BaseDTO implements OutputConverter<UserExperiencesDTO, UserExperiences> {

    private Long id;

    private Long uid;

    private String name;

    private String awards;

    private String role;

    private String projectUrl;

    private String description;

    private String startTime;

    private String endTime;
}
