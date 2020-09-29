package run.ut.app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * ForumDTO
 * </p>
 *
 * @author wenjie
 * @since 2020-05-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ForumDTO 对象", description = "")
public class ForumDTO extends BaseDTO {

      @JsonSerialize(using = ToStringSerializer.class)
      private Long id;

      @ApiModelProperty(value = "版块名称")
      private String name;
}
