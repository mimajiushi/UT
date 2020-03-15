package run.ut.app.service;

import run.ut.app.model.domain.Tags;
import run.ut.app.model.domain.TeamsRecruitmentsTags;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  TeamsRecruitmentsTagsService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-15
 */
public interface TeamsRecruitmentsTagsService extends IService<TeamsRecruitmentsTags> {

    List<Tags> listByTeamsRecruitmentsId(Long teamRecruitmentId);

    void deleteByTeamsRecruitmentsId(Long teamRecruitmentId);
}
