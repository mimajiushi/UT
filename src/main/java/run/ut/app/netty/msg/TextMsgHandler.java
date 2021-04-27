package run.ut.app.netty.msg;

import io.netty.channel.ChannelHandlerContext;
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
 * text msg handler
 *
 * @author chenwenjie.star
 * @date 2021/4/26 11:35 上午
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TextMsgHandler implements ClientMsgHandler {

    private final UserChannelManager userChannelManager;
    private final ChatHistoryService chatHistoryService;

    @Override
    public void handle(ChannelHandlerContext ctx, WebSocketMsgTypeEnum typeEnum, ChatHistoryDTO chatHistoryDTO) {
        if (!support(typeEnum)) {
            return;
        }
        Long uid = Long.valueOf(ctx.channel().attr(AttributeKey.valueOf("uid")).get().toString());
        // 先根据时间戳（long类型）查询消息，如果该时间戳类型的时间存在，且内容一样，那么直接返回ack
        chatHistoryDTO.setFromUid(uid);
        ChatHistory chatHistoryOld = chatHistoryService.getOneByFromUidAndTimeStamp(uid, chatHistoryDTO.getTimeStamp());
        if (!ObjectUtils.isEmpty(chatHistoryOld) && chatHistoryOld.getContent().equals(chatHistoryDTO.getContent())) {
            userChannelManager.writeAndFlush(
                    uid, chatHistoryOld.getId(), WebSocketMsgTypeEnum.SERVER_ACK
            );
            return;
        }
        // 持久化消息
        ChatHistory chatHistory = chatHistoryDTO.convertTo();
        chatHistoryService.save(chatHistory);
        chatHistoryDTO = chatHistory.convertTo();
        // 返回server-ack给发送者
        userChannelManager.writeAndFlushServerAck(chatHistoryDTO);
        // 用户在线则推送，客户端确认接收后会返回ack，那时候消息才会更改为已读状态
        userChannelManager.writeAndFlushChatMsg(chatHistoryDTO);
    }

    @Override
    public boolean support(WebSocketMsgTypeEnum typeEnum) {
        return typeEnum.equals(WebSocketMsgTypeEnum.TEXT_MSG);
    }
}
