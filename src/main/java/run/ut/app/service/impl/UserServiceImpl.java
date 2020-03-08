package run.ut.app.service.impl;

import run.ut.app.model.domain.User;
import run.ut.app.mapper.UserMapper;
import run.ut.app.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenjie
 * @since 2020-03-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
