package run.ut.app.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import run.ut.app.utils.SpringUtils;


/**
 * @author wenjie
 */

@Slf4j
public class ClientMsgHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private UserChannelManager userChannelManager;

    ClientMsgHandler() {
        userChannelManager = SpringUtils.getBean(UserChannelManager.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg)
        throws Exception {
        String text = msg.text();

        // It could be an attack frames from a hacker
        if (StringUtils.isBlank(text)) {
            ctx.channel().close();
        }
        userChannelManager.writeAndFlush(123L, "通知xxxxxx");
    }

}
