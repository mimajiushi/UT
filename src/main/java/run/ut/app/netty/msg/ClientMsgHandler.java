package run.ut.app.netty.msg;

import io.netty.channel.ChannelHandlerContext;
import run.ut.app.model.dto.ChatHistoryDTO;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;

/**
 * @author chenwenjie.star
 * @date 2021/4/25 6:20 下午
 */
public interface ClientMsgHandler {

    void handle(ChannelHandlerContext ctx, WebSocketMsgTypeEnum typeEnum, ChatHistoryDTO chatHistoryDTO);

    boolean support(WebSocketMsgTypeEnum typeEnum);

}
