package run.ut.app.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.domain.Teams;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.domain.TeamsMembers;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsDTO;
import run.ut.app.model.param.LeaveParam;
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

    /**
     * Create team
     *
     * @param teamsParam    team info
     * @param leaderId      leader id
     * @return TeamsDTO
     */
    @NonNull
    TeamsDTO createTeam(@NonNull TeamsParam teamsParam, @NonNull Long leaderId);

    /**
     * Save team tags
     *
     * @param tagIds     tag ids
     * @param leaderId   leader id
     * @param teamsId    team id
     * @return TagsDTO list.
     */
    @NonNull
    List<TagsDTO> saveTeamsTags(String[] tagIds, @NonNull Long leaderId, @NonNull Long teamsId);

    @NonNull
    @Deprecated
    List<TagsDTO> saveTeamsRecruitmentsTags(@Nullable String[] tagIds, @NonNull Long teamRecruitmentId);

    /**
     * Update team logo
     * @param logo       logo (MultipartFile)
     * @param leaderId   leader id
     * @param teamsId    team id
     * @return ok result with message
     */
    @NonNull
    BaseResponse<String> updateTeamsLogo(@NonNull MultipartFile logo, @NonNull Long leaderId, @NonNull Long teamsId);

    /**
     * Update team base info
     *
     * @param teamsParam    params
     * @param leaderId      leader uid
     * @return              ok result with message
     */
    @NonNull
    BaseResponse<String> updateTeamsBaseInfo(@NonNull TeamsParam teamsParam, @NonNull Long leaderId);


    /**
     * Get team by leader id and team id
     *
     * @param leaderId  leader id
     * @param teamsId   team id
     * @return Teams
     */
    @NonNull
    Teams getTeamByLeaderIdAndTeamId(@NonNull Long leaderId, @NonNull Long teamsId);

    /**
     * User issues a request to team
     *
     * @param teamApplyParam application parameters
     * @return ok result with message
     */
    @NonNull
    BaseResponse<String> userApplyToTeam(@NonNull TeamApplyOrInviteParam teamApplyParam);

    /**
     * Team invites user
     *
     * @param teamInviteParam Invitation parameters
     * @return ok result with message
     */
    @NonNull
    BaseResponse<String> teamInvitesUser(@NonNull TeamApplyOrInviteParam teamInviteParam);

    /**
     * List team by uid
     *
     * @param uid uid
     * @return TeamsDTO list
     */
    @NonNull
    List<TeamsDTO> listTeamsByUid(@NonNull Long uid);

    /**
     * Member fired or Members leave voluntarily, depending on run.ut.app.model.param.LeaveParam#mode
     *
     * @param leaveParam params
     * @return ok result with message
     */
    @NonNull
    BaseResponse<String> leave(@NonNull LeaveParam leaveParam);

    /**
     * Get team by leader's uid and team's id, if could not found team by uid, throw NotFoundException
     *
     * @param leaderId leader id
     * @param teamsId team id
     * @return Teams
     */
    @Nullable
    Teams getAndCheckTeamByLeaderIdAndTeamId(@NonNull Long leaderId, @NonNull Long teamsId);

    /**
     * List team ids by leader id
     *
     * @param leaderId leader id
     * @return list of team ids
     */
    @Nullable
    List<Long> getTeamIdsByLeaderId(@NonNull Long leaderId);

    /**
     * List team by leader id
     *
     * @param leaderId leader id
     * @return TeamsDTO list
     */
    @NonNull
    List<TeamsDTO> listTeamsByLeaderId(@NonNull Long leaderId);

    /**
     * Get TeamMembers obj by uid and team id
     *
     * @param uid       uid
     * @param teamId    team id
     * @return
     */
    @Nullable
    TeamsMembers getMemberByUidAndTeamId(Long uid, Long teamId);

    /**
     * Transfer team's leader
     *
     * @param leaderId    leader
     * @param targetUid   target uid
     * @param teamId      teams id
     * @return            ok result with message
     */
    @NonNull
    BaseResponse<String> transferLeader(@NonNull Long leaderId, @NonNull Long targetUid, @NonNull Long teamId);

    /**
     * Disband team
     *
     * @param teamId   team id
     * @return         ok result with message
     */
    @NonNull
    BaseResponse<String> disband(@NonNull Long leaderId, @NonNull Long teamId);
}
