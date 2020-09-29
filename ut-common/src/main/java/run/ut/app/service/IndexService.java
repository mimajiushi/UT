package run.ut.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.lang.NonNull;
import run.ut.app.model.dto.TeamsRecruitmentsDTO;
import run.ut.app.model.param.SearchRecruitmentParam;
import run.ut.app.model.param.SearchStudentParam;
import run.ut.app.model.param.SearchTeamParam;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.StudentVO;
import run.ut.app.model.vo.TeamVO;
import run.ut.app.model.vo.TeamsRecruitmentsVO;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author wenjie
 */


public interface IndexService {

    /**
     * list students by param
     *
     * @param searchStudentParam  Search parameters
     * @param page                Paging object of mybatis
     * @return student vo list
     */
    @NonNull
    CommentPage<StudentVO> listStudentByParam(@NonNull SearchStudentParam searchStudentParam, @NonNull Page page);

    /**
     * list teams by param
     *
     * @param searchTeamParam     Search parameters
     * @param page                Paging object of mybatis
     * @return team vo list
     */
    @NonNull
    CommentPage<TeamVO> listTeamByParam(@NonNull SearchTeamParam searchTeamParam, @NonNull Page page);

    /**
     * list recruitments by param
     *
     * @param searchRecruitmentParam     Search parameters
     * @param page                       Paging object of mybatis
     * @return recruitment vo list
     */
    CommentPage<TeamsRecruitmentsVO> listRecruitmentByParam(@NonNull SearchRecruitmentParam searchRecruitmentParam, @NonNull Page page);

    /**
     * Get user info by uid
     *
     * @param uid user's id
     * @return User's(Student's) non-private information
     */
    @NonNull
    StudentVO showUserPageInfo(@NonNull Long uid);

    /**
     * Get team info by team's id.
     * @param teamsId team's id
     * @return TeamVO
     */
    @NonNull
    TeamVO showTeamsInfo(@NonNull Long teamsId);

    /**
     * Get recruitment info by recruitment's id
     * @param recruitmentsId recruitment's id
     * @return TeamsRecruitmentsVO
     */
    @NonNull
    TeamsRecruitmentsVO showRecruitmentsInfo(@NonNull Long recruitmentsId);

    /**
     * Get all recruitments of the team by team's id
     * @param teamId team's id
     * @return TeamsRecruitmentsDTO list
     */
    @NonNull
    List<TeamsRecruitmentsDTO> listRecruitmentsByTeamId(@Nonnull Long teamId);

}
