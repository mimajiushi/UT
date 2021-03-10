package run.ut.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.TeamsMembers;
import run.ut.app.model.domain.User;

import java.util.List;

/**
 * <p>
 *  TeamsMembersService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-14
 */
public interface TeamsMembersService extends IService<TeamsMembers> {

    /**
     * Get team's members by team's id
     * @param teamsId teams id
     * @return TeamsMembers list
     */
    @NonNull
    List<TeamsMembers> listByTeamsId(@NonNull Long teamsId);

    /**
     * count of user's teams
     *
     * @param uid      user's id
     * @param teamId   team's id
     * @return count
     */
    @NonNull
    Integer countByUid(@NonNull Long uid, @NonNull Long teamId);

    /**
     * count of leader's teams
     *
     * @param leaderId   leader's id
     * @param teamId     team's id
     * @return count
     */
    @NonNull
    Integer countByLeaderId(@NonNull Long leaderId, @NonNull Long teamId);

    /**
     * Get leader's info by team's id
     * @param teamsId team's id
     * @return leader info
     */
    @NonNull
    User getLeaderByTeamsId(@NonNull Long teamsId);
}
