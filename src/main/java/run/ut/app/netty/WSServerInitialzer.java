package run.ut.app.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import run.ut.app.config.netty.WebSocketConfiguration;
import run.ut.app.utils.SpringUtils;

import java.util.concurrent.TimeUnit;

/**
 * Init Channel
 *
 * @author wenjie
 */
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        WebSocketConfiguration webSocketConfiguration = SpringUtils.getBean(WebSocketConfiguration.class);

        ChannelPipeline pipeline = ch.pipeline();

        pipeline
            .addLast(new WebSocketRateLimitHandler())
            .addLast(new HttpServerCodec())
            .addLast(new ChunkedWriteHandler())
            .addLast(new HttpObjectAggregator(1024 * 64))
            .addLast(new AuthHandler())
//            .addLast(new IdleStateHandler(30, 30, 5, TimeUnit.SECONDS))
            .addLast(new IdleStateHandler(10, 10, 30, TimeUnit.MINUTES))
            .addLast(new HeartBeatHandler())
            .addLast(new WebSocketServerProtocolHandler(webSocketConfiguration.getContextPath()))
            .addLast(new ClientMsgHandler());
    }

}
