package run.ut.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.domain.TeamsRecruitmentsTags;

import java.util.List;

/**
 * <p>
 *  TeamsRecruitmentsTagsService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-15
 */
@Deprecated
public interface TeamsRecruitmentsTagsService extends IService<TeamsRecruitmentsTags> {

    @NonNull
    List<Tags> listByTeamsRecruitmentsId(@NonNull Long teamRecruitmentId);

    @NonNull
    void deleteByTeamsRecruitmentsId(@NonNull Long teamRecruitmentId);
}
