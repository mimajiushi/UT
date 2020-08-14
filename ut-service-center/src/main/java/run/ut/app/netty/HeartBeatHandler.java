package run.ut.app.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Heart Beat Handler. If the client's heartbeat frames is not received for a long time
 *
 * @author wenjie
 */

@ChannelHandler.Sharable
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private final UserChannelManager userChannelManager;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;

            if (event.state() == IdleState.READER_IDLE) {
                log.debug("READER_IDLE...");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.debug("WRITER_IDLE...");
            } else if (event.state() == IdleState.ALL_IDLE) {
                // Closes the channel which state is ALL_IDLE
                Channel channel = ctx.channel();
                // Clear cache
                userChannelManager.remove(channel);
                channel.close();
                log.debug("close channel: {}", channel.id().asLongText());
            }
        }

    }

}
