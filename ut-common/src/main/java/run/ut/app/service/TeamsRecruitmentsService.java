package run.ut.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.TeamsRecruitments;
import run.ut.app.model.dto.TeamsRecruitmentsDTO;
import run.ut.app.model.param.TeamsRecruitmentsParam;
import run.ut.app.model.support.BaseResponse;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * <p>
 *  TeamsRecruitmentsService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
public interface TeamsRecruitmentsService extends IService<TeamsRecruitments> {

    /**
     * Save team's recruitments
     *
     * @param teamsRecruitmentsParam recruitment's info
     * @return TeamsRecruitmentsDTO and tips
     */
    @NonNull
    BaseResponse<TeamsRecruitmentsDTO> saveTeamsRecruitment(@NonNull TeamsRecruitmentsParam teamsRecruitmentsParam);

    /**
     * List recruitments by team id
     *
     * @param teamsId team id
     * @return TeamsRecruitments list
     */
    @Nonnull
    List<TeamsRecruitments> listTeamsRecruitmentsByTeamsId(@NonNull Long teamsId);

    /**
     * Remove recruitment by id
     *
     * @param teamId             team id
     * @param recruitmentId      recruitment Id
     * @return                   ok result with message
     */
    @NonNull
    BaseResponse<TeamsRecruitmentsDTO> removeRecruitment(@NonNull Long teamId, @NonNull Long recruitmentId);

}
