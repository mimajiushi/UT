package run.ut.app.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import run.ut.app.service.RedisService;
import run.ut.app.utils.SpringUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebSocketRateLimitHandler extends SimpleChannelInboundHandler {

    private RedisService redisService;
    private final int EXPIRE_TIME = 5;
    private final int MAX = 20;


    WebSocketRateLimitHandler() {
        this.redisService = SpringUtils.getBean(RedisService.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = insocket.getAddress().getHostAddress();
        // Generates key
        String key = String.format("ut_wss_limit_rate_%s", ip);
        // Checks
        boolean over = redisService.overRequestRateLimit(key, EXPIRE_TIME, MAX, TimeUnit.SECONDS, "websocket");
        if (over) {
            ctx.channel().close();
        }
    }
}
