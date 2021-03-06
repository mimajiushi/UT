package run.ut.app.model.param;

import lombok.Data;
import run.ut.app.model.domain.Options;
import run.ut.app.model.dto.base.InputConverter;
import run.ut.app.model.enums.OptionsType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Optional param.
 *
 * @author wenjie
 */
@Data
public class OptionsParam implements InputConverter<Options>, Serializable {

    @NotBlank(message = "Option key must not be blank")
    @Size(max = 100, message = "Length of option key must not be more than {max}")
    private String optionKey;


    @Size(max = 1023, message = "Length of option value must not be more than {max}")
    private String optionValue;

    private OptionsType type;
}
