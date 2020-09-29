package run.ut.app.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import run.ut.app.api.admin.AdminOptionControllerApi;
import run.ut.app.controller.BaseController;
import run.ut.app.model.dto.OptionsDTO;
import run.ut.app.model.enums.OptionsEnum;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.security.CheckAuthorization;
import run.ut.app.service.OptionsService;

import java.util.List;
import java.util.Map;

/**
 * Option Controller.
 *
 * @author wenjie
 * @date 2020-6-26
 */
@RestController
@Slf4j
@RequestMapping("admin/option")
@CheckAuthorization(roles = UserRolesEnum.ROLE_ADMIN)
public class AdminOptionController extends BaseController implements AdminOptionControllerApi {

    @DubboReference
    private OptionsService optionsService;

    @Override
    @GetMapping("listAll")
    public List<OptionsDTO> listAll() {
        return optionsService.listDtos();
    }

    @Override
    @GetMapping("listAllWithMapView")
    public Map<String, Object> listAllWithMapView() throws JsonProcessingException {
        return optionsService.listOptions();
    }

    @Override
    @GetMapping("emailOptions")
    public List<OptionsDTO> emailOptions() {
        return optionsService.listDtos(OptionsEnum.EMAIL_OPTIONS.getKeysList());
    }

    @Override
    @GetMapping("attachmentOptions")
    public List<OptionsDTO> attachmentOptions() {
        return optionsService.listDtos(OptionsEnum.OSS_QNYUN_OPTIONS.getKeysList());
    }

    @Override
    @GetMapping("wechatMPOptions")
    public List<OptionsDTO> wechatMPOptions() {
        return optionsService.listDtos(OptionsEnum.WECHAT_MP.getKeysList());
    }

    @Override
    @PostMapping("saveOptionsWithMapView")
    public BaseResponse<String> saveOptionsWithMapView(@RequestBody Map<String, Object> optionMap) {
        optionsService.save(optionMap);
        return BaseResponse.ok("保存成功");
    }
}
