package run.ut.app.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.domain.Teams;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsDTO;
import run.ut.app.model.param.TeamApplyOrInviteParam;
import run.ut.app.model.param.TeamsParam;
import run.ut.app.model.support.BaseResponse;

import java.util.List;

/**
 * <p>
 *  ITeamsService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
public interface TeamsService extends IService<Teams> {

    @NonNull
    TeamsDTO createTeam(@NonNull TeamsParam teamsParam, @NonNull Long leaderId, @NonNull MultipartFile logo);

    @NonNull
    List<TagsDTO> saveTeamsTags(String[] tagIds, @NonNull Long leaderId, @NonNull Long teamsId);

    @NonNull
    List<TagsDTO> saveTeamsRecruitmentsTags(@Nullable String[] tagIds, @NonNull Long teamRecruitmentId);

    @NonNull
    BaseResponse<String> updateTeamsLogo(@NonNull MultipartFile logo, @NonNull Long leaderId, @NonNull Long teamsId);

    @NonNull
    Teams getTeamByLeaderIdAndTeamId(@NonNull Long leaderId, @NonNull Long TeamsId);

    @NonNull
    BaseResponse<String> userApplyToTeam(@NonNull TeamApplyOrInviteParam teamApplyParam);

    @NonNull
    BaseResponse<String> teamInvitesUser(@NonNull TeamApplyOrInviteParam teamInviteParam);

    /**
     * Get team by leader's uid and team's id, if could not found team by uid, throw NotFoundException
     * @param leaderId leader's uid
     */
    @NonNull
    Teams getAndCheckTeamByLeaderIdAndTeamId(@NonNull Long leaderId, @NonNull Long TeamsId);

    @NonNull
    List<Long> getTeamIdsByLeaderId(@NonNull Long leaderId);

    @NonNull
    List<TeamsDTO> listTeamsByLeaderId(@NonNull Long leaderId);
}
