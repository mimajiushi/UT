package run.ut.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.UserExperiences;
import run.ut.app.model.dto.UserExperiencesDTO;
import run.ut.app.model.param.UserExperiencesParam;
import run.ut.app.model.support.BaseResponse;

import java.util.List;

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
    @NonNull
    UserExperiencesDTO saveUserExperiences(@NonNull UserExperiencesParam userExperiencesParam);

    /**
     * delete user's experience
     */
    @NonNull
    BaseResponse<String> deleteUserExperiences(@NonNull Long uid, @NonNull String id);

    /**
     * get user's experiences list by uid
     */
    @NonNull
    List<UserExperiences> getUserExperiencesByUid(@NonNull Long uid);


}
