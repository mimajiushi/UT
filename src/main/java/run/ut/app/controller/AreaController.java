package run.ut.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.AreaControllerApi;
import run.ut.app.model.dto.DataAreaDTO;
import run.ut.app.model.dto.DataSchoolDTO;
import run.ut.app.service.DataAreaService;
import run.ut.app.service.DataSchoolService;
import run.ut.app.utils.BeanUtils;

import java.util.List;

/**
 * @author wenjie
 */

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("area")
public class AreaController implements AreaControllerApi {

    private final DataAreaService dataAreaService;
    private final DataSchoolService dataSchoolService;

    /**
     * List All Area by provinceId
     */
    @GetMapping("/{id}/child")
    @Override
    public List<DataAreaDTO> getCityByParentId(@PathVariable Integer id) throws Exception {
        return BeanUtils.transformFromInBatch(dataAreaService.getAreaDataByParentId(id), DataAreaDTO.class);
    }


    /**
     * List All School by provinceId
     */
    @GetMapping("/school/{provinceId}")
    @Override
    public List<DataSchoolDTO> getSchoolByProvinceId(@PathVariable Integer provinceId) {
        return BeanUtils.transformFromInBatch(dataSchoolService.getListByProvinceId(provinceId), DataSchoolDTO.class);
    }

    /**
     *
     */
    @GetMapping("/school/info/{schoolId}")
    @Override
    public DataSchoolDTO getSchoolInfoById(@PathVariable String schoolId) {
        return new DataSchoolDTO().convertFrom(dataSchoolService.getById(schoolId));
    }


    /**
     * test api
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
