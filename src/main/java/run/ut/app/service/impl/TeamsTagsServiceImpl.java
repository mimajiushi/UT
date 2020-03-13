package run.ut.app.service.impl;

import run.ut.app.model.domain.TeamsTags;
import run.ut.app.mapper.TeamsTagsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.service.TeamsTagsService;

/**
 * <p>
 *  TeamsTagsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
@Service
public class TeamsTagsServiceImpl extends ServiceImpl<TeamsTagsMapper, TeamsTags> implements TeamsTagsService {

}
