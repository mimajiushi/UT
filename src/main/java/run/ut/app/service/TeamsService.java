package run.ut.app.service;

import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.domain.Teams;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsDTO;
import run.ut.app.model.param.TeamsParam;

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

    TeamsDTO createTeam(TeamsParam teamsParam, Long leaderId, MultipartFile logo) throws HttpMediaTypeNotAcceptableException;

    List<TagsDTO> saveTeamsTags(String[] tagIds, Long leaderId);

    Teams getTeamByLeaderId(Long leaderId);

}
