package run.ut.app.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import run.ut.app.model.domain.Forum;
import run.ut.app.model.dto.base.InputConverter;

/**
 * @author wenjie
 * @date 2020-5-19
 */
@Data
public class ForumParam implements InputConverter<Forum> {

    @ApiModelProperty(value = "版块id")
    private Long id;

    @ApiModelProperty(value = "版块名称")
    private String name;
}
