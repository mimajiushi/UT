package run.ut.app.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.ut.app.service.RedisService;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author wenjie
 */

@Slf4j
@ChannelHandler.Sharable
@Component
public class WebSocketRateLimitHandler extends ChannelInboundHandlerAdapter {

    @DubboReference
    private RedisService redisService;

    private final int EXPIRE_TIME = 5;
    private final int MAX = 10;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = insocket.getAddress().getHostAddress();
        // Generates key
        String key = String.format("ut_wss_limit_rate_%s", ip);
        // Checks
        boolean over = redisService.overRequestRateLimit(key, MAX, EXPIRE_TIME, TimeUnit.SECONDS, "websocket");
        if (over) {
            log.debug("IP: {} 触发限流了 ",ip);
            ctx.channel().close();
            return;
        }
        ctx.fireChannelRead(msg);
    }
}
