package run.ut.app.service;

import run.ut.app.model.domain.TeamsRecruitments;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.dto.TeamsRecruitmentsDTO;
import run.ut.app.model.param.TeamsRecruitmentsParam;
import run.ut.app.model.support.BaseResponse;

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

    BaseResponse<TeamsRecruitmentsDTO> saveTeamsRecruitment(TeamsRecruitmentsParam teamsRecruitmentsParam);

    List<TeamsRecruitments> listTeamsRecruitmentsByTeamsId(Long teamsId);

}
