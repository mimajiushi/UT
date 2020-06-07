package run.ut.app.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;
import run.ut.app.model.support.WebSocketMsg;
import run.ut.app.utils.JsonUtils;
import run.ut.app.utils.SpringUtils;


/**
 * @author wenjie
 */

@ChannelHandler.Sharable
@Slf4j
public class ClientMsgHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private UserChannelManager userChannelManager;

    ClientMsgHandler() {
        userChannelManager = SpringUtils.getBean(UserChannelManager.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg)
        throws Exception {
        String json = msg.text();
        WebSocketMsg webSocketMsg = JsonUtils.jsonToObject(json, WebSocketMsg.class);
        WebSocketMsgTypeEnum type = WebSocketMsgTypeEnum.getByType(webSocketMsg.getType());
        switch (type) {
            case KEEPALIVE:
                log.debug("Get keepalive frame");
        }
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
