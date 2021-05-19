package run.ut.app.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.dto.ChatHistoryDTO;
import run.ut.app.model.dto.base.InputConverter;

/**
 * <p>
 *    chat history
 * </p>
 *
 * @author chenwenjie.star
 * @since 2021-04-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "聊天记录对象", description = "")
public class ChatHistory extends BaseEntity implements InputConverter<ChatHistoryDTO> {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 发送者id
     */
    private Long fromUid;

    /**
     * 接受者uid
     */
    private Long toUid;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 0 - 未读 1 - 已读
     */
    private Integer msgRead;

    /**
     * 客户端传的时间戳
     */
    private Long timeStamp;


}
