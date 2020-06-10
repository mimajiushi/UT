package run.ut.app.netty;

import io.jsonwebtoken.Claims;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import run.ut.app.security.util.JwtOperator;
import run.ut.app.utils.SpringUtils;

/**
 * FullHttpRequest
 */

@ChannelHandler.Sharable
@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {

    private UserChannelManager userChannelManager;
    private JwtOperator jwtOperator;

    AuthHandler() {
        userChannelManager = SpringUtils.getBean(UserChannelManager.class);
        jwtOperator = SpringUtils.getBean(JwtOperator.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            HttpHeaders headers = request.headers();
            if (headers.size() < 1) {
                ctx.channel().close();
                return;
            }
            String token = headers.get("token");
            Claims claims = jwtOperator.getClaimsFromToken(token);
            Long uid = Long.valueOf(claims.get("uid") + "");
            ctx.channel().attr(AttributeKey.newInstance("LOGIN")).setIfAbsent("true");
            userChannelManager.add(uid, ctx.channel());
            log.debug("Authentication success. uid: " + uid);
            ctx.pipeline().remove(this);
            ctx.fireChannelRead(msg);
        } else {
            ctx.channel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        userChannelManager.remove(ctx.channel());
        ctx.channel().close();
    }
}
