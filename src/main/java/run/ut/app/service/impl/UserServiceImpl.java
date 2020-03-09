package run.ut.app.service.impl;

import run.ut.app.model.domain.User;
import run.ut.app.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.service.UserService;

/**
 * <p>
 *  UserServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
