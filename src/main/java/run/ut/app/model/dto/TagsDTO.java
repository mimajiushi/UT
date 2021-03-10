package run.ut.app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * <p>
 * tags DTO
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
public class TagsDTO extends BaseDTO implements OutputConverter<TagsDTO, Tags> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer parentId;

    private String name;

    private Integer level;
}
