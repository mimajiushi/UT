package run.ut.app.api.area;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import run.ut.app.model.dto.DataAreaDTO;
import run.ut.app.model.dto.DataSchoolDTO;

import java.util.List;


@Api(value="获取地址相关api",tags = "地址controller")
public interface AreaControllerApi {

    @ApiOperation("获取地址列表")
    public List<DataAreaDTO> getAreaDataByParentId(@PathVariable Integer id) throws Exception;

    @ApiOperation("根据行政id（provinceId）获取该区的学校信息列表")
    public List<DataSchoolDTO> getSchoolByProvinceId(@PathVariable Integer provinceId);

    @ApiOperation("根据id获取学校详细信息")
    public DataSchoolDTO getSchoolInfoById(@PathVariable String schoolId);
}
