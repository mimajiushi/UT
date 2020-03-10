package run.ut.app.model.param;

import lombok.Data;
import run.ut.app.model.enums.OptionsType;

/**
 * Option query params.
 *
 * @author wenjie
 */
@Data
public class OptionsQuery {

    private String keyword;

    private OptionsType type;
}
