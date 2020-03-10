package run.ut.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.ut.app.model.domain.Options;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * Option output dto.
 *
 * @author wenjie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionsDTO implements OutputConverter<OptionsDTO, Options> {

    private String key;

    private Object value;

}
