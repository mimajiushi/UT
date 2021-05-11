package run.ut.app.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author chenwenjie.star
 * @version 1.0
 * @date 2021/4/28 20:18
 */

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ChatHistoryParam 对象", description = "获取聊天记录对象")
public class ChatHistoryParam implements Serializable {

    @ApiModelProperty(value = "聊天对象uid")
    @NotNull(message = "聊天对象uid不能为空")
    private Long targetUid;

    @ApiModelProperty(value = "消息id", notes = "消息id为空则获取最近历史消息，不为空则根据type获取历史/最新消息")
    private Long chatId;

    @ApiModelProperty(value = "获取消息数")
    private Integer count = 20;
}
