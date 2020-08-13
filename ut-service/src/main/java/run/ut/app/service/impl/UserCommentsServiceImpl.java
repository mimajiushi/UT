package run.ut.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import run.ut.app.model.domain.UserComments;
import run.ut.app.mapper.UserCommentsMapper;
import run.ut.app.service.UserCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  UserCommentsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-19
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserCommentsServiceImpl extends ServiceImpl<UserCommentsMapper, UserComments> implements UserCommentsService {

}
