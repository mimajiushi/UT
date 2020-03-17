package run.ut.app.service;

import org.springframework.lang.NonNull;
import run.ut.app.model.domain.TeamsMembers;
import com.baomidou.mybatisplus.extension.service.IService;

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

    @NonNull
    List<TeamsMembers> listByTeamsId(@NonNull Long teamsId);

}
