package run.ut.app.service.impl;

import run.ut.app.model.domain.UserInfo;
import run.ut.app.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.service.UserInfoService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenjie
 * @since 2020-03-09
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
