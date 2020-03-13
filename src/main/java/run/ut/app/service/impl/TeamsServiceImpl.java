package run.ut.app.service.impl;

import run.ut.app.model.domain.Teams;
import run.ut.app.mapper.TeamsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.service.TeamsService;

/**
 * <p>
 *  TeamsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
@Service
public class TeamsServiceImpl extends ServiceImpl<TeamsMapper, Teams> implements TeamsService {

}
