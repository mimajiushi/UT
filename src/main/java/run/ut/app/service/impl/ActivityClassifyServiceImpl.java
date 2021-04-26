package run.ut.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import run.ut.app.exception.AlreadyExistsException;
import run.ut.app.mapper.ActivityClassifyMapper;
import run.ut.app.model.domain.ActivityClassify;
import run.ut.app.model.dto.ActivityClassifyDTO;
import run.ut.app.model.param.ActivityClassifyParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.ActivityClassifyService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动分类 服务实现类
 * </p>
 *
 * @author chenwenjie.star
 * @since 2021-03-10
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActivityClassifyServiceImpl extends ServiceImpl<ActivityClassifyMapper, ActivityClassify> implements ActivityClassifyService {

    @Override
    public List<ActivityClassifyDTO> getAllClassify() {
        return lambdaQuery().orderByDesc(ActivityClassify::getUpdateTime).list()
                .stream().map(e -> new ActivityClassifyDTO().convertFrom(e)).collect(Collectors.toList());
    }

    @Override
    public BaseResponse<ActivityClassifyDTO> saveClassify(ActivityClassifyParam param) {
        // insert
        if (param.getId() == null) {
            ActivityClassify old = lambdaQuery()
                    .eq(ActivityClassify::getId, param.getId())
                    .or()
                    .eq(ActivityClassify::getCname, param.getCname()).one();
            if (!ObjectUtils.isEmpty(old)) {
                throw new AlreadyExistsException("该分类id/名称已存在");
            }
            save(param.convertTo());
        }
        // update
        else {
            updateById(param.convertTo());
        }
        return BaseResponse.ok(new ActivityClassifyDTO().convertFrom(param.convertTo()));
    }
}
