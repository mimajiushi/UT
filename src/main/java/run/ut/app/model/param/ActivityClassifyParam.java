package run.ut.app.model.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.ActivityClassify;
import run.ut.app.model.domain.BaseEntity;
import run.ut.app.model.dto.BaseDTO;
import run.ut.app.model.dto.base.InputConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
