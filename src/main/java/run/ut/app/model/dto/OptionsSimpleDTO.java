package run.ut.app.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import run.ut.app.model.enums.OptionsType;

import java.util.Date;

/**
 * Option list output dto.
 *
 * @author wenjie
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OptionsSimpleDTO extends OptionsDTO {

    private Integer id;

    private OptionsType type;

    private Date createTime;

    private Date updateTime;
}
