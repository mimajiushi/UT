package run.ut.app.netty;

import io.jsonwebtoken.Claims;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;
import run.ut.app.model.support.WebSocketMsg;
import run.ut.app.security.util.JwtOperator;
import run.ut.app.utils.JsonUtils;
import run.ut.app.utils.SpringUtils;

/**
 *  When the client connects for the first time, it obtains token verification first, and then deletes itself.
 *
 * @author wenjie
 */
@Slf4j
public class AuthHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private UserChannelManager userChannelManager;
    private JwtOperator jwtOperator;

    AuthHandler() {
        userChannelManager = SpringUtils.getBean(UserChannelManager.class);
        jwtOperator = SpringUtils.getBean(JwtOperator.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String json = msg.text();
        WebSocketMsg webSocketMsg = JsonUtils.jsonToObject(json, WebSocketMsg.class);
        String token = webSocketMsg.getMsg() + "";
        WebSocketMsgTypeEnum type = WebSocketMsgTypeEnum.getByType(webSocketMsg.getType());
        if (WebSocketMsgTypeEnum.AUTH != type || !jwtOperator.validateToken(token)) {
            log.debug("Authentication failed!");
            ctx.channel().close();
        } else {
            Claims claims = jwtOperator.getClaimsFromToken(token);
            Long uid = Long.valueOf(claims.get("uid") + "");
            userChannelManager.add(uid, ctx.channel());
            log.debug("Authentication success. uid: " + uid);
            ctx.pipeline().remove(this);
        }

//        if (!"token".equals(token)) {
//            log.debug("auth fail!");
//            ctx.channel().close();
//        } else {
//            log.debug("auth success");
//            userChannelManager.add(123L, ctx.channel());
//            ctx.pipeline().remove(this);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        userChannelManager.remove(ctx.channel());
    }
}
