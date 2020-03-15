package run.ut.app.service;

import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.domain.Teams;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsDTO;
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

    TeamsDTO createTeam(TeamsParam teamsParam, Long leaderId, MultipartFile logo);

    List<TagsDTO> saveTeamsTags(String[] tagIds, Long leaderId, Long teamsId);

    BaseResponse<String> updateTeamsLogo(MultipartFile logo, Long leaderId, Long teamsId);

    Teams getTeamByLeaderIdAndTeamId(Long leaderId, Long TeamsId);

    /**
     * Get team by leader's uid and team's id, if could not found team by uid, throw NotFoundException
     * @param leaderId leader's uid
     */
    Teams getAndCheckTeamByLeaderIdAndTeamId(Long leaderId, Long TeamsId);
}
