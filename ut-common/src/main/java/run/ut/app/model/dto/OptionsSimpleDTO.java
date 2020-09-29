package run.ut.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import run.ut.app.model.enums.OptionsType;

import java.util.Date;

/**
 * Option list output dto.
 *
 * @author wenjie
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OptionsSimpleDTO extends OptionsDTO {

    private Integer id;

    private OptionsType type;

    private Date createTime;

    private Date updateTime;
}
