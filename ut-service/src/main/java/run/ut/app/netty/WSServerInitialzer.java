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

    private AuthHandler authHandler;
    private ClientMsgHandler clientMsgHandler;
    private HeartBeatHandler heartBeatHandler;
    private WebSocketRateLimitHandler webSocketRateLimitHandler;

    WSServerInitialzer() {
        authHandler = SpringUtils.getBean(AuthHandler.class);
        clientMsgHandler = SpringUtils.getBean(ClientMsgHandler.class);
        heartBeatHandler = SpringUtils.getBean(HeartBeatHandler.class);
        webSocketRateLimitHandler = SpringUtils.getBean(WebSocketRateLimitHandler.class);
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        WebSocketConfiguration webSocketConfiguration = SpringUtils.getBean(WebSocketConfiguration.class);

        ChannelPipeline pipeline = ch.pipeline();

        pipeline
            .addLast(webSocketRateLimitHandler)
            .addLast(new HttpServerCodec())
            .addLast(new ChunkedWriteHandler())
            .addLast(new HttpObjectAggregator(1024 * 64))
            .addLast(authHandler)
//            .addLast(new IdleStateHandler(30, 30, 5, TimeUnit.SECONDS))
            .addLast(new IdleStateHandler(10, 10, 30, TimeUnit.MINUTES))
            .addLast(heartBeatHandler)
            .addLast(new WebSocketServerProtocolHandler(webSocketConfiguration.getContextPath()))
            .addLast(clientMsgHandler);
    }

}
