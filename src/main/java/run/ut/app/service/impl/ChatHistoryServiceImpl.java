package run.ut.app.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import run.ut.app.mapper.ChatHistoryMapper;
import run.ut.app.model.domain.ChatHistory;
import run.ut.app.model.dto.ChatHistoryDTO;
import run.ut.app.model.dto.base.InputConverter;
import run.ut.app.model.enums.MsgReadStatusEnum;
import run.ut.app.model.param.ChatHistoryParam;
import run.ut.app.service.ChatHistoryService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  chat history impl
 * </p>
 *
 * @author chenwenjie.star
 * @since 2021-04-26
 */
@Service
public class ChatHistoryServiceImpl extends ServiceImpl<ChatHistoryMapper, ChatHistory> implements ChatHistoryService {

    @Override
    public ChatHistory getOneByFromUidAndTimeStamp(Long uid, Long timeStamp) {
        return lambdaQuery()
                .eq(ChatHistory::getFromUid, uid)
                .eq(ChatHistory::getTimeStamp, timeStamp)
                .last("limit 1").one();
    }

    @Override
    public List<ChatHistoryDTO> listSingleChatHistory(Long selfUid, ChatHistoryParam chatHistoryParam) {
        Long toUid = chatHistoryParam.getTargetUid();
        Long chatId = chatHistoryParam.getChatId();
        LambdaQueryChainWrapper<ChatHistory> lambdaQuery = lambdaQuery()
                .eq(ChatHistory::getFromUid, selfUid)
                .eq(ChatHistory::getToUid, toUid)
                .last(String.format("limit %s", chatHistoryParam.getCount()));
        // 消息id不为空则获取 新 + 未读 消息
        if (ObjectUtils.isEmpty(chatId)) {
            lambdaQuery.ge(ChatHistory::getId, chatId);
        } else {
            lambdaQuery.eq(ChatHistory::getMsgRead, MsgReadStatusEnum.UN_READ.getType());
            lambdaQuery.gt(ChatHistory::getId, chatId);
        }

        return lambdaQuery.list()
                .stream()
                .map(InputConverter::convertTo)
                .collect(Collectors.toList());
    }
}
