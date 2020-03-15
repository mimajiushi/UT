package run.ut.app.service.impl;

import run.ut.app.model.domain.TeamsMembers;
import run.ut.app.mapper.TeamsMembersMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.service.TeamsMembersService;

/**
 * <p>
 *  TeamsMembersServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-14
 */
@Service
public class TeamsMembersServiceImpl extends ServiceImpl<TeamsMembersMapper, TeamsMembers> implements TeamsMembersService {

}
