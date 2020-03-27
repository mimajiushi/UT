package run.ut.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import run.ut.app.model.param.SearchRecruitmentParam;
import run.ut.app.model.param.SearchStudentParam;
import run.ut.app.model.param.SearchTeamParam;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.StudentVO;
import run.ut.app.model.vo.TeamVO;
import run.ut.app.model.vo.TeamsRecruitmentsVO;

/**
 * @author wenjie
 */


public interface IndexService {

    /**
     * list students by param
     * @return student vo list
     */
    @NonNull
    CommentPage<StudentVO> listStudentByParam(@NonNull SearchStudentParam searchStudentParam, @NonNull Page page);

    /**
     * list teams by param
     * @return team vo list
     */
    @NonNull
    CommentPage<TeamVO> listTeamByParam(@NonNull SearchTeamParam searchTeamParam, @NonNull Page page);

    /**
     * list recruitments by param
     * @return recruitment vo list
     */
    CommentPage<TeamsRecruitmentsVO> listRecruitmentByParam(@NonNull SearchRecruitmentParam searchRecruitmentParam, @NonNull Page page);

    /**
     * @return Student's non-private information
     */
    @NonNull
    StudentVO showUserPageInfo(@NonNull Long uid);

    @NonNull
    TeamVO showTeamsInfo(@NonNull Long teamsId);

    @NonNull
    TeamsRecruitmentsVO showRecruitmentsInfo(@NonNull Long recruitmentsId);

}
