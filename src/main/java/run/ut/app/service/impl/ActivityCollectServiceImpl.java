package run.ut.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import run.ut.app.model.domain.ActivityCollect;
import run.ut.app.mapper.ActivityCollectMapper;
import run.ut.app.service.ActivityCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  ActivityCollectServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActivityCollectServiceImpl extends ServiceImpl<ActivityCollectMapper, ActivityCollect> implements ActivityCollectService {

}
