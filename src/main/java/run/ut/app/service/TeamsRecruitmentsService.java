package run.ut.app.service;

import org.springframework.lang.NonNull;
import run.ut.app.model.domain.TeamsRecruitments;
import com.baomidou.mybatisplus.extension.service.IService;
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

    @NonNull
    BaseResponse<TeamsRecruitmentsDTO> saveTeamsRecruitment(@NonNull TeamsRecruitmentsParam teamsRecruitmentsParam);

    @Nonnull
    List<TeamsRecruitments> listTeamsRecruitmentsByTeamsId(@NonNull Long teamsId);

}
