package run.ut.app.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import run.ut.app.utils.SpringUtils;

/**
 *  When the client connects for the first time, it obtains token verification first, and then deletes itself.
 *
 * @author wenjie
 */
@Slf4j
public class AuthHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private UserChannelManager userChannelManager;

    AuthHandler() {
        userChannelManager = SpringUtils.getBean(UserChannelManager.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        if (!"token".equals(text)) {
            log.debug("auth fail!");
            ctx.channel().close();
        } else {
            log.debug("auth success");
            userChannelManager.add(123L, ctx.channel());
            ctx.pipeline().remove(this);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        userChannelManager.remove(ctx.channel());
    }
}
