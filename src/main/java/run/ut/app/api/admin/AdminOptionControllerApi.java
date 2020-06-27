package run.ut.app.api.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.dto.OptionsDTO;
import run.ut.app.model.support.BaseResponse;

import java.util.List;
import java.util.Map;

@Api(value = "管理员管理系统配置项API",tags = "管理员管理系统配置项API")
public interface AdminOptionControllerApi {

    @ApiOperation(value = "获取所有系统配置列表", notes = "key-value")
    List<OptionsDTO> listAll();

    @ApiOperation(value = "获取所有系统配置列表", notes = "Map格式")
    Map<String, Object> listAllWithMapView() throws JsonProcessingException;

    @ApiOperation(value = "获取邮箱配置")
    List<OptionsDTO> emailOptions();

    @ApiOperation(value = "获取附件配置", notes = "只有七牛云")
    List<OptionsDTO> attachmentOptions();

    @ApiOperation(value = "保存配置")
    BaseResponse<String> saveOptionsWithMapView(Map<String, Object> optionMap);
}
