package run.ut.app.service;

import run.ut.app.model.domain.UserExperiences;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.dto.UserExperiencesDTO;
import run.ut.app.model.param.UserExperiencesParam;

/**
 * <p>
 *  UserExperiencesService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-11
 */
public interface UserExperiencesService extends IService<UserExperiences> {


    /**
     * insert or update user's experiences
     */
    public UserExperiencesDTO saveUserExperiences(UserExperiencesParam userExperiencesParam);
}
