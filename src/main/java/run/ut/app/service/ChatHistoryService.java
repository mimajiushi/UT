package run.ut.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.ut.app.model.domain.ChatHistory;
import run.ut.app.model.dto.ChatHistoryDTO;
import run.ut.app.model.param.ChatHistoryParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenwenjie.star
 * @since 2021-04-26
 */
public interface ChatHistoryService extends IService<ChatHistory> {

    /**
     * Get one chat history by from_uid and time stamp
     *
     * @param uid          from uid
     * @param timeStamp    time stamp
     * @return             entity of chat history
     */
    @Nullable
    ChatHistory getOneByFromUidAndTimeStamp(@NonNull Long uid, @NonNull Long timeStamp);

    /**
     * List chat history by params (single chat)
     *
     * @param selfUid            self uid
     * @param chatHistoryParam   request params
     * @return                   list of chat history
     */
    @Nullable
    List<ChatHistoryDTO> listSingleChatHistory(@NonNull Long selfUid, @NonNull ChatHistoryParam chatHistoryParam);


}
