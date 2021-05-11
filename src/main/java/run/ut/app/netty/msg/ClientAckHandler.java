package run.ut.app.netty.msg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import run.ut.app.model.domain.ChatHistory;
import run.ut.app.model.dto.ChatHistoryDTO;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;
import run.ut.app.netty.UserChannelManager;
import run.ut.app.service.ChatHistoryService;

/**
 * client msg handler
 *
 * @author chenwenjie.star
 * @date 2021/4/26 11:35 上午
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientAckHandler implements ClientMsgHandler {

    private final ChatHistoryService chatHistoryService;

    @Override
    public void handle(ChannelHandlerContext ctx, WebSocketMsgTypeEnum typeEnum, ChatHistoryDTO chatHistoryDTO) {
        if (!support(typeEnum)) {
            return;
        }
        if (ObjectUtils.isEmpty(chatHistoryDTO) || ObjectUtils.isEmpty(chatHistoryDTO.getId())) {
            return;
        }
        Long chatId = chatHistoryDTO.getId();
        Attribute<Object> attr = ctx.channel().attr(AttributeKey.valueOf(chatId.toString()));
        if (ObjectUtils.isEmpty(attr)) {
            return;
        }
        attr.set(null);
        // todo 设置数据库消息已读
        log.debug("接收到消息id为[{}]的ack", chatId);
    }

    @Override
    public boolean support(WebSocketMsgTypeEnum typeEnum) {
        return typeEnum.equals(WebSocketMsgTypeEnum.CLIENT_ACK);
    }
}
