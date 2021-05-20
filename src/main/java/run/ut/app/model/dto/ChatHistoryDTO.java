package run.ut.app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.ChatHistory;
import run.ut.app.model.dto.base.InputConverter;
import run.ut.app.model.dto.base.OutputConverter;

/**
 * <p>
 *    chat history DTO
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
@ApiModel(value = "ChatHistoryDTO 对象", description = "")
public class ChatHistoryDTO extends BaseDTO
        implements OutputConverter<ChatHistoryDTO, ChatHistory>, InputConverter<ChatHistory> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 发送者id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fromUid;

    /**
     * 接受者uid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long toUid;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     * @see run.ut.app.model.enums.WebSocketMsgTypeEnum
     */
    private Integer type;

    /**
     * 0 - 未读 1 - 已读
     */
    private Integer msgRead;

    /**
     * 客户端传的时间戳
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long timeStamp;


}
