package run.ut.app.model.dto;

import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.DataArea;
import run.ut.app.model.dto.base.OutputConverter;
import run.ut.app.model.enums.DataAreaLevelEnum;

import java.io.Serializable;

/**
 * <p>
 * 行政区域数据表
 * </p>
 *
 * @author wenjie
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataAreaDTO implements OutputConverter<DataAreaDTO, DataArea>, Serializable {

    private Integer id;

    private Integer parentId;

    private String name;

    private String shortName;

    private Float longitude;

    private Float latitude;

    private DataAreaLevelEnum level;

    private Integer sort;

    private Integer status;
}
