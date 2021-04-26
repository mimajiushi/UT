package run.ut.app.netty.msg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author chenwenjie.star
 * @date 2021/4/25 6:31 下午
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientMsgHandlers {

    private final Collection<ClientMsgHandler> clientMsgHandlers = new LinkedList<>();

    public ClientMsgHandlers(ApplicationContext applicationContext) {
        // Add all file handler
        addClientMsgHandlers(applicationContext.getBeansOfType(ClientMsgHandler.class).values());
    }


    /**
     * Adds client msg handlers.
     *
     * @param clientMsgHandlers client msg handler collection
     * @return current file client handlers
     */
    @NonNull
    public ClientMsgHandlers addClientMsgHandlers(@Nullable Collection<ClientMsgHandler> clientMsgHandlers) {
        if (!CollectionUtils.isEmpty(clientMsgHandlers)) {
            this.clientMsgHandlers.addAll(clientMsgHandlers);
        }
        return this;
    }
}
