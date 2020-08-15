package run.ut.app.model.dto;

import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.Options;
import run.ut.app.model.dto.base.OutputConverter;

import java.io.Serializable;

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
public class OptionsDTO implements OutputConverter<OptionsDTO, Options>, Serializable {

    private String key;

    private Object value;

    private String remark;

    public OptionsDTO(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
