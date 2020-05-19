package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import run.ut.app.exception.AlreadyExistsException;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.ForumMapper;
import run.ut.app.model.domain.Forum;
import run.ut.app.model.param.ForumParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.ForumService;

/**
 * <p>
 * ForumServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-19
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ForumServiceImpl extends ServiceImpl<ForumMapper, Forum> implements ForumService {

    @Override
    public BaseResponse<String> saveForum(ForumParam forumParam) {
        boolean insert = forumParam.getId() == null;
        Forum forum = forumParam.convertTo();

        Forum forum2 = getOne(new QueryWrapper<Forum>().eq("name", forum.getName()));
        if (!ObjectUtils.isEmpty(forum2)) {
            throw new AlreadyExistsException("版块已存在");
        }
        if (insert) {
            boolean save = save(forum);
            return save ? BaseResponse.ok("新增版块成功~") : BaseResponse.ok("请稍后再试");
        } else {
            Forum forum1 = getById(forum.getId());
            if (ObjectUtils.isEmpty(forum1)) {
                throw new NotFoundException("找不到更新的版块~");
            }
            boolean update = updateById(forum1);
            return update ? BaseResponse.ok("更新成功") : BaseResponse.ok("请稍后重试~");
        }
    }

    @Override
    public BaseResponse<String> removeForum(Long forumId) {
        boolean res = removeById(forumId);
        return res ? BaseResponse.ok("删除成功~") : BaseResponse.ok("请稍后再试~");
    }
}
