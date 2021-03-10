package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.ActivityClassify;
import run.ut.app.model.dto.base.InputConverter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 活动分类
 * </p>
 *
 * @author chenwenjie.star
 * @since 2021-03-10
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "活动分类", description = "")
public class ActivityClassifyParam implements Serializable, InputConverter<ActivityClassify> {

    private Long id;

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    private String cname;
}
