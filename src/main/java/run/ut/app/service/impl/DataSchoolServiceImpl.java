package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import run.ut.app.config.redis.RedisKey;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.DataSchoolMapper;
import run.ut.app.model.domain.DataSchool;
import run.ut.app.service.DataSchoolService;
import run.ut.app.service.RedisService;
import run.ut.app.utils.JsonUtils;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataSchoolServiceImpl extends ServiceImpl<DataSchoolMapper, DataSchool> implements DataSchoolService {

    private final DataSchoolMapper dataSchoolMapper;
    private final RedisService redisService;

    @Override
    public List<DataSchool> getListByProvinceId(Integer provinceId) {
        String key = RedisKey.SCHOOL_DATA_LIST_PREFIX + "::" + provinceId;
        String value = redisService.get(key);
        if (!StringUtils.hasText(value)) {
            List<DataSchool> dataSchools = dataSchoolMapper.selectList(
                    new QueryWrapper<DataSchool>().eq("province_id", provinceId)
            );
            if (!ObjectUtils.isEmpty(dataSchools)) {
                redisService.setKeyValTTL(key, JsonUtils.objectToJson(dataSchools), RedisKey.AREA_TTL);
                return dataSchools;
            }
            throw new NotFoundException("The schools with id " + provinceId + " could not be found or have been deleted");
        }
        return JsonUtils.jsonToList(value, DataSchool.class);
    }


    /**
     * 获取所有学校的行政区id (去重)
     */
    @Override
    public List<Integer> selectProvincIdDistinct() {
        return dataSchoolMapper.selectProvincIdDistinct();
    }

    @Override
    public DataSchool getById(Integer id) {
        String key = RedisKey.SCHOOL_DATA_PREFIX + "::" + id;
        String value = redisService.get(key);
        if (!StringUtils.hasText(value)) {
            DataSchool dataSchool = dataSchoolMapper.selectById(id);
            if (!ObjectUtils.isEmpty(dataSchool)) {
                redisService.setKeyValTTL(key, JsonUtils.objectToJson(dataSchool), RedisKey.AREA_TTL);
                return dataSchool;
            }
            throw new NotFoundException("The school with id " + id + " could not be found or has been deleted");
        }
        return JsonUtils.jsonToObject(value, DataSchool.class);
    }

    @Override
    public List<DataSchool> getAllSchool() {
        return dataSchoolMapper.selectList(null);
    }
}
