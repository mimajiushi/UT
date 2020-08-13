package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.dto.BaseDTO;
import run.ut.app.model.dto.base.InputConverter;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * TagsParam
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
@ApiModel(value = "TagsParam", description = "")
public class TagsParam extends BaseDTO implements InputConverter<Tags> {

    private Integer id;

    @Deprecated
    private Integer parentId;

    @NotBlank(message = "标签名不能为空")
    private String name;
}
