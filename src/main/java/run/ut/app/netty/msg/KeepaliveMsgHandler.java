package run.ut.app.netty.msg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import run.ut.app.model.dto.ChatHistoryDTO;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;

/**
 * keep-alive frame handler
 *
 * @author chenwenjie.star
 * @date 2021/4/26 11:35 上午
 */
@Slf4j
@Component
public class KeepaliveMsgHandler implements ClientMsgHandler {

    @Override
    public void handle(WebSocketMsgTypeEnum typeEnum, ChatHistoryDTO chatHistoryDTO) {
        if (!support(typeEnum)) {
            return;
        }
        log.debug("Get keepalive frame");
    }

    @Override
    public boolean support(WebSocketMsgTypeEnum typeEnum) {
        return typeEnum.equals(WebSocketMsgTypeEnum.KEEPALIVE);
    }
}
