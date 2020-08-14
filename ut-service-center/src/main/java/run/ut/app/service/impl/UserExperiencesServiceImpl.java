package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import run.ut.app.model.domain.UserExperiences;
import run.ut.app.mapper.UserExperiencesMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.model.dto.UserExperiencesDTO;
import run.ut.app.model.param.UserExperiencesParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.UserExperiencesService;

import java.util.List;

/**
 * <p>
 *  UserExperiencesServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DubboService
public class UserExperiencesServiceImpl extends ServiceImpl<UserExperiencesMapper, UserExperiences> implements UserExperiencesService {


    @Override
    public UserExperiencesDTO saveUserExperiences(UserExperiencesParam userExperiencesParam) {

        UserExperiences userExperiences = userExperiencesParam.convertTo();
        // Update if the id is not empty
        if (null != userExperiences.getId()) {
            userExperiences.setUpdateTime(null);
            update(userExperiences, new QueryWrapper<UserExperiences>()
                    .eq("id", userExperiences.getId())
                    .eq("uid", userExperiences.getUid()));
            return new UserExperiencesDTO().convertFrom(userExperiences);
        }

        // Insert if the id is empty
        save(userExperiences);
        return new UserExperiencesDTO().convertFrom(userExperiences);
    }

    @Override
    public BaseResponse<String> deleteUserExperiences(Long uid, String id) {
        boolean removed = remove(new QueryWrapper<UserExperiences>().eq("uid", uid).eq("id", id));
        return removed ? BaseResponse.ok("删除成功") : BaseResponse.ok("删除失败");
    }

    @Override
    public List<UserExperiences> getUserExperiencesByUid(Long uid) {
        return list(new QueryWrapper<UserExperiences>().eq("uid", uid).orderByDesc("create_time"));
    }

}
