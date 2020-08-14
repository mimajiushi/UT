package run.ut.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import run.ut.app.model.domain.UserPosts;
import run.ut.app.mapper.UserPostsMapper;
import run.ut.app.service.UserPostsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  UserPostsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DubboService
public class UserPostsServiceImpl extends ServiceImpl<UserPostsMapper, UserPosts> implements UserPostsService {

}
