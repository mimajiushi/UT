package run.ut.app.model.dto;

import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.Options;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * Option output dto.
 *
 * @author wenjie
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionsDTO implements OutputConverter<OptionsDTO, Options> {

    private String key;

    private Object value;

}
