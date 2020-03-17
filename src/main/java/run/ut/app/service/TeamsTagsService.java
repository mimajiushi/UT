package run.ut.app.service;

import org.springframework.lang.NonNull;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.domain.TeamsTags;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  TeamsTagsService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
public interface TeamsTagsService extends IService<TeamsTags> {

    @NonNull
    List<Tags> listByTeamsId(@NonNull Long teamsId);

    void deleteByTeamsId(@NonNull Long teamsId);
}
