package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import run.ut.app.model.dto.ChatHistoryDTO;
import run.ut.app.model.param.ChatHistoryParam;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wenjie
 */
@Api(value = "聊天记录API")
public interface ChatHistoryControllerApi {

    @ApiOperation(value = "获取单聊聊天记录")
    List<ChatHistoryDTO> listSingleChatHistory(@RequestBody @Valid ChatHistoryParam chatHistoryParam);
}
