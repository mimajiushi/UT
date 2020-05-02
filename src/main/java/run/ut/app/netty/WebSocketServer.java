package run.ut.app.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.ut.app.config.netty.WebSocketConfiguration;

/**
 * WebSocket Server
 *
 * @author wenjie
 */

@Component
@Slf4j
public class WebSocketServer {

    private final static WebSocketServer INSTANCE = new WebSocketServer();

    public static WebSocketServer getInstance() {
        return INSTANCE;
    }

    private ServerBootstrap server;

    public WebSocketServer() {
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new WSServerInitialzer());
    }

    public void start(int port) throws InterruptedException {
        log.info("WebSocketServer - Starting...");
        server.bind(port).sync();
        log.info("WebSocketServer - Start completed.");
    }
}