package run.ut.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.ut.app.mapper.ActivityClassifyMapper;
import run.ut.app.model.domain.ActivityClassify;
import run.ut.app.service.ActivityClassifyService;

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

}
