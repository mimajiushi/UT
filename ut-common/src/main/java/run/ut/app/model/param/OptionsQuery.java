package run.ut.app.model.param;

import lombok.Data;
import run.ut.app.model.enums.OptionsType;

import java.io.Serializable;

/**
 * Option query params.
 *
 * @author wenjie
 */
@Data
public class OptionsQuery implements Serializable {

    private String keyword;

    private OptionsType type;
}
