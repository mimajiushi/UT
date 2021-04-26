package run.ut.app.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.ut.app.model.domain.ChatHistory;
import com.baomidou.mybatisplus.extension.service.IService;

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

    @Nullable
    ChatHistory getOneByFromUidAndTimeStamp(@NonNull Long uid, @NonNull Long timeStamp);

}
