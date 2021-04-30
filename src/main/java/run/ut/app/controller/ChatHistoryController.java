package run.ut.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.ChatHistoryControllerApi;
import run.ut.app.model.dto.ChatHistoryDTO;
import run.ut.app.model.param.ChatHistoryParam;
import run.ut.app.security.CheckLogin;
import run.ut.app.service.ChatHistoryService;

import java.util.List;

/**
 * @author wenjie
 * @date 2020-5-17
 */
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("chat")
public class ChatHistoryController extends BaseController implements ChatHistoryControllerApi {

    private final ChatHistoryService chatHistoryService;


    @Override
    @GetMapping("list/chat/history/single")
    @CheckLogin
    public List<ChatHistoryDTO> listSingleChatHistory(ChatHistoryParam chatHistoryParam) {
        Long selfUid = getUid();
        return null;
    }
}
