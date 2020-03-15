package run.ut.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import run.ut.app.model.domain.UserTeamApplyLog;
import run.ut.app.mapper.UserTeamApplyLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.service.UserTeamApplyLogService;

/**
 * <p>
 *  UserTeamApplyLogServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserTeamApplyLogServiceImpl extends ServiceImpl<UserTeamApplyLogMapper, UserTeamApplyLog> implements UserTeamApplyLogService {

}
