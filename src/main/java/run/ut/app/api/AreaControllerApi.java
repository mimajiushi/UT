package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import run.ut.app.model.dto.DataAreaDTO;
import run.ut.app.model.dto.DataSchoolDTO;

import java.util.List;

@Api(value="获取地址相关API",tags = "获取地址相关API")
public interface AreaControllerApi {

    @ApiOperation("获取地址列表（传入0则是获取所有省，传入省id则是返回省下的所有市，传入市id则是返回市下所有区）")
    public List<DataAreaDTO> getCityByParentId(@PathVariable Integer id) throws Exception;

    @ApiOperation("根据行政id（provinceId）获取该区的学校信息列表")
    public List<DataSchoolDTO> getSchoolByProvinceId(@PathVariable Integer provinceId);

    @ApiOperation("根据id获取学校详细信息")
    public DataSchoolDTO getSchoolInfoById(@PathVariable String schoolId);
}
