package run.ut.app.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.ut.app.model.dto.ChatHistoryDTO;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;
import run.ut.app.netty.msg.ClientMsgHandlers;
import run.ut.app.utils.JsonUtils;


/**
 * @author wenjie
 */

@ChannelHandler.Sharable
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientMsgHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final UserChannelManager userChannelManager;
    private final ClientMsgHandlers clientMsgHandlers;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg)
        throws Exception {
        String json = msg.text();
        ChatHistoryDTO chatHistoryDTO = JsonUtils.jsonToObject(json, ChatHistoryDTO.class);
        WebSocketMsgTypeEnum type = WebSocketMsgTypeEnum.getByType(chatHistoryDTO.getType());
        clientMsgHandlers.handle(type, chatHistoryDTO);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        userChannelManager.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        userChannelManager.remove(ctx.channel());
    }
}
