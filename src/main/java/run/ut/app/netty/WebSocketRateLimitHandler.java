package run.ut.app.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import run.ut.app.service.RedisService;
import run.ut.app.utils.SpringUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author wenjie
 */

@Slf4j
public class WebSocketRateLimitHandler extends ChannelInboundHandlerAdapter {

    private RedisService redisService;
    private final int EXPIRE_TIME = 5;
    private final int MAX = 10;


    WebSocketRateLimitHandler() {
        this.redisService = SpringUtils.getBean(RedisService.class);
    }

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
