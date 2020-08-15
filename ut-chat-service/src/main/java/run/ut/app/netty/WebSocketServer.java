package run.ut.app.netty;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceInstance;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import run.ut.app.config.netty.WebSocketConfiguration;
import run.ut.app.exception.WebSocketException;

/**
 * WebSocket Server
 *
 * @author wenjie
 */

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSocketServer {

    private final NacosServiceRegistry nacosServiceRegistry;
    private final WebSocketConfiguration webSocketConfiguration;
    private final NacosDiscoveryProperties nacosDiscoveryProperties;
    private final ApplicationContext context;

    private ServerBootstrap server;

    public void init() {
        EventLoopGroup bossGroup, workerGroup;
        server = new ServerBootstrap();

        int bossThreads = webSocketConfiguration.getBossThreads();
        int workerThreads = webSocketConfiguration.getWorkerThreads();
        boolean epoll = webSocketConfiguration.isEpoll();

        if (epoll) {
            bossGroup = new EpollEventLoopGroup(bossThreads,
                new DefaultThreadFactory("WebSocketBossGroup", true));
            workerGroup = new EpollEventLoopGroup(workerThreads,
                new DefaultThreadFactory("WebSocketWorkerGroup", true));
            server.channel(EpollServerSocketChannel.class);
        } else {
            bossGroup = new NioEventLoopGroup(bossThreads);
            workerGroup = new NioEventLoopGroup(workerThreads);
            server.channel(NioServerSocketChannel.class);
        }

        server.group(bossGroup, workerGroup)
            .childHandler(new WSServerInitialzer())
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    public void start() throws Exception {
        log.info("WebSocketServer - Starting...");
        ChannelFuture channelFuture = server.bind(webSocketConfiguration.getPort()).sync();
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                nacosServiceRegistry.register(buildNacosRegistration());
                log.info("WebSocketServer - Start completed.");
            } else {
                throw new WebSocketException("WebSocket启动失败！");
            }
        });
    }

    private NacosRegistration buildNacosRegistration() {
        NacosDiscoveryProperties newNacosDiscoveryProperties = new NacosDiscoveryProperties();
        BeanUtils.copyProperties(nacosDiscoveryProperties, newNacosDiscoveryProperties);
        newNacosDiscoveryProperties.setPort(webSocketConfiguration.getPort());
        newNacosDiscoveryProperties.setService("ut-chat-service-websocket");
        return new NacosRegistration(newNacosDiscoveryProperties, context);
    }

}